package com.toy.common.network;

import com.toy.common.message.ResponseMessage;
import com.toy.common.message.RpcFailure;
import com.toy.common.message.RpcResponse;
import com.toy.common.network.util.NettyUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on
 * 2017/6/21.
 */
public class TransportResponseHandler implements MessageHandler<ResponseMessage> {
    private static final Logger logger = LoggerFactory.getLogger(TransportRequestHandler.class);
    private Channel channel;
    private final Map<Long, RpcResponseCallback> outstandingRpcs;
    private final AtomicLong timeOfLastRequestNs;

    public TransportResponseHandler(Channel channel) {
        this.channel = channel;
        this.outstandingRpcs = new ConcurrentHashMap<>();
        this.timeOfLastRequestNs = new AtomicLong(0L);
    }

    @Override
    public void handle(ResponseMessage message) throws Exception {
        if (message instanceof RpcResponse) {
            RpcResponse resp = (RpcResponse) message;
            RpcResponseCallback listener = outstandingRpcs.get(resp.getRequestId());
            if (listener == null) {
                logger.warn("Ignoring response for RPC {} from {} ({} bytes) since it is not outstanding",
                        resp.getRequestId(), NettyUtil.getRemoteAddress(channel), resp.body().size());
            } else {
                outstandingRpcs.remove(resp.getRequestId());
                try {
                    listener.onSuccess(resp.body().nioByteBuffer());
                } finally {
                    resp.body().release();
                }
            }
        } else if (message instanceof RpcFailure) {
            RpcFailure resp = (RpcFailure) message;
            RpcResponseCallback listener = outstandingRpcs.get(resp.requestId);
            if (listener == null) {
                logger.warn("Ignoring response for RPC {} from {} ({}) since it is not outstanding",
                        resp.requestId, NettyUtil.getRemoteAddress(channel), resp.errorString);
            } else {
                outstandingRpcs.remove(resp.requestId);
                listener.onFailure(new RuntimeException(resp.errorString));
            }
        }
    }

    @Override
    public void channelActive() {
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        if (numOutstandingRequests() > 0) {
            String remoteAddress = NettyUtil.getRemoteAddress(channel);
            logger.error("Still have {} requests outstanding when connection from {} is closed",
                    numOutstandingRequests(), remoteAddress);
            failOutstandingRequests(cause);
        }
    }

    private void failOutstandingRequests(Throwable cause) {
        for (Map.Entry<Long, RpcResponseCallback> entry : outstandingRpcs.entrySet()) {
            entry.getValue().onFailure(cause);
        }
        // It's OK if new fetches appear, as they will fail immediately.
        outstandingRpcs.clear();
    }

    @Override
    public void channelInactive() {
        if (numOutstandingRequests() > 0) {
            String remoteAddress = NettyUtil.getRemoteAddress(channel);
            logger.error("Still have {} requests outstanding when connection from {} is closed",
                    numOutstandingRequests(), remoteAddress);
            failOutstandingRequests(new IOException("Connection from " + remoteAddress + " closed"));
        }
    }

    public void addRpcRequest(long requestId, RpcResponseCallback callback) {
        this.updateTimeOfLastRequest();
        outstandingRpcs.put(requestId, callback);
    }

    public long getTimeOfLastRequestNs() {
        return this.timeOfLastRequestNs.get();
    }

    public void updateTimeOfLastRequest() {
        this.timeOfLastRequestNs.set(System.nanoTime());
    }

    /**
     * Returns total number of outstanding requests (fetch requests + rpcs)
     */
    public int numOutstandingRequests() {
        return outstandingRpcs.size();
    }
}
