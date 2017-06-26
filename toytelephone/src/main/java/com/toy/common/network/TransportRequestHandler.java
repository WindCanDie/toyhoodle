package com.toy.common.network;

import com.google.common.base.Throwables;
import com.toy.common.message.*;
import com.toy.common.message.buffer.NioMessageBuffer;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.nio.ByteBuffer;


/**
 * Created by Administrator on
 * 2017/6/21.
 */
public class TransportRequestHandler implements MessageHandler<RequestMessage> {
    private static final Logger logger = LoggerFactory.getLogger(TransportRequestHandler.class);
    private final RpcHandler rpcHandler;
    private final Channel channel;
    private final TransportClient client;


    public TransportRequestHandler(Channel channel, TransportClient client, RpcHandler rpcHandler) {
        this.rpcHandler = rpcHandler;
        this.channel = channel;
        this.client = client;
    }

    @Override
    public void handle(RequestMessage message) throws Exception {
        if (message instanceof OneWayMessage) {
            processOneWayMessage((OneWayMessage) message);
        } else if (message instanceof RpcRequest) {
            processRpcRequest((RpcRequest) message);
        }
    }

    private void processOneWayMessage(OneWayMessage req) {
        try {
            rpcHandler.receive(client, req.body().nioByteBuffer());
        } catch (Exception e) {
            logger.error("Error while invoking RpcHandler#receive() for one-way message.", e);
        } finally {
            req.body().release();
        }
    }

    private void processRpcRequest(final RpcRequest req) {
        try {
            rpcHandler.receive(client, req.body().nioByteBuffer(), new RpcResponseCallback() {
                @Override
                public void onSuccess(ByteBuffer response) {
                    respond(new RpcResponse(req.getRequestId(), new NioMessageBuffer(response)));
                }

                @Override
                public void onFailure(Throwable e) {
                    respond(new RpcFailure(req.getRequestId(), Throwables.getStackTraceAsString(e)));
                }
            });
        } catch (Exception e) {
            logger.error("Error while invoking RpcHandler#receive() on RPC id " + req.getRequestId(), e);
            respond(new RpcFailure(req.getRequestId(), Throwables.getStackTraceAsString(e)));
        } finally {
            req.body().release();
        }
    }

    @Override
    public void channelActive() {
        this.rpcHandler.channelActive(this.client);
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        this.rpcHandler.exceptionCaught(cause, this.client);
    }

    @Override
    public void channelInactive() {
        this.rpcHandler.channelInactive(this.client);
    }

    /**
     * Responds to a single message with some Encodable object. If a failure occurs while sending,
     * it will be logged and the channel closed.
     */
    private void respond(Message result) {
        SocketAddress remoteAddress = channel.remoteAddress();
        channel.writeAndFlush(result).addListener(future -> {
            if (future.isSuccess()) {
                logger.trace("Sent result {} to client {}", result, remoteAddress);
            } else {
                logger.error(String.format("Error sending result %s to %s; closing connection",
                        result, remoteAddress), future.cause());
                channel.close();
            }
        });
    }
}
