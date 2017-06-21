package com.toy.common.network;

import com.toy.common.message.RequestMessage;
import io.netty.channel.Channel;


/**
 * Created by Administrator on
 * 2017/6/21.
 */
public class TransportRequestHandler implements MessageHandler<RequestMessage> {
    public TransportRequestHandler(Channel channel, TransportClient client) {

    }

    @Override
    public void handle(RequestMessage message) throws Exception {

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
}
