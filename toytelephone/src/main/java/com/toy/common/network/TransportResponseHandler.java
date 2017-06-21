package com.toy.common.network;

import com.toy.common.message.ResponseMessage;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on
 * 2017/6/21.
 */
public class TransportResponseHandler implements MessageHandler<ResponseMessage> {
    private Channel channel;
    private final Map<Long, RpcResponseCallback> outstandingRpcs;

    public TransportResponseHandler(Channel channel) {
        this.channel = channel;
        this.outstandingRpcs = new ConcurrentHashMap<>();
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
        outstandingRpcs.put(requestId, callback);
    }
}
