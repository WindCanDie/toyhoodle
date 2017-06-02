package com.toy.common.network;


import io.netty.channel.Channel;

/**
 * Created by Administrator
 * on 2017/6/2.
 */
public class TransportClient {
    private Channel channel;

    public TransportClient(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isAction() {
        return channel.isOpen() || channel.isActive();
    }


}
