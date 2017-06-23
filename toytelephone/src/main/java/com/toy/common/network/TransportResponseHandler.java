package com.toy.common.network;

import com.toy.common.message.ResponseMessage;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on
 * 2017/6/21.
 */
public class TransportResponseHandler implements MessageHandler<ResponseMessage> {
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

    }

    @Override
    public void channelActive() {

    }

    @Override
    public void exceptionCaught(Throwable cause) {

    }

    @Override
    public void channelInactive() {

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
