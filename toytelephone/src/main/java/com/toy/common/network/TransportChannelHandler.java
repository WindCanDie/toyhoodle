package com.toy.common.network;

import com.toy.common.message.RequestMessage;
import com.toy.common.message.ResponseMessage;
import com.toy.common.network.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.toy.common.network.util.NettyUtil.getRemoteAddress;

/**
 * Created by Administrator on
 * 2017/6/2.
 */
public class TransportChannelHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TransportChannelHandler.class);
    private final TransportClient client;
    private final TransportRequestHandler requestHandler;
    private final TransportResponseHandler responseHandler;
    private final long requestTimeoutNs;
    private final long confrequestTimeoutMs = 120L;
    private final boolean closeIdleConnections;

    public TransportChannelHandler(TransportClient client, TransportRequestHandler requestHandler,
                                   TransportResponseHandler responseHandler, boolean closeIdleConnections) {
        this.client = client;
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
        this.requestTimeoutNs = confrequestTimeoutMs * 1000L * 1000L;
        this.closeIdleConnections = closeIdleConnections;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            synchronized (this) {
                boolean isActuallyOverdue = System.nanoTime() - this.responseHandler.getTimeOfLastRequestNs() > this.requestTimeoutNs;
                if (e.state() == IdleState.ALL_IDLE && isActuallyOverdue) {
                    if (this.responseHandler.numOutstandingRequests() > 0) {
                        String address = NettyUtil.getRemoteAddress(ctx.channel());
                        logger.error("Connection to {} has been quiet for {} ms while there are outstanding requests. Assuming connection is dead; please adjust spark.network.timeout if this is wrong.", address, Long.valueOf(this.requestTimeoutNs / 1000L / 1000L));
                        this.client.timeOut();
                        ctx.close();
                    } else if (this.closeIdleConnections) {
                        this.client.timeOut();
                        ctx.close();
                    }
                }
            }
        }

        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Exception in connection from " + getRemoteAddress(ctx.channel()),
                cause);
        requestHandler.exceptionCaught(cause);
        responseHandler.exceptionCaught(cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            requestHandler.channelActive();
        } catch (RuntimeException e) {
            logger.error("Exception from request handler while channel is active", e);
        }
        try {
            responseHandler.channelActive();
        } catch (RuntimeException e) {
            logger.error("Exception from response handler while channel is active", e);
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            requestHandler.channelInactive();
        } catch (RuntimeException e) {
            logger.error("Exception from request handler while channel is inactive", e);
        }
        try {
            responseHandler.channelInactive();
        } catch (RuntimeException e) {
            logger.error("Exception from response handler while channel is inactive", e);
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object request) throws Exception {
        if (request instanceof RequestMessage) {
            requestHandler.handle((RequestMessage) request);
        } else if (request instanceof ResponseMessage) {
            responseHandler.handle((ResponseMessage) request);
        } else {
            ctx.fireChannelRead(request);
        }
    }

    public TransportClient getClient() {
        return client;
    }

    public TransportRequestHandler getRequestHandler() {
        return requestHandler;
    }

    public TransportResponseHandler getResponseHandler() {
        return responseHandler;
    }
}
