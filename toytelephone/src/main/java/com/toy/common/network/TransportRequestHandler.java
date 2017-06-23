package com.toy.common.network;

import com.toy.common.message.RequestMessage;
import io.netty.channel.Channel;


/**
 * Created by Administrator on
 * 2017/6/21.
 */
public class TransportRequestHandler implements MessageHandler<RequestMessage> {
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

}
