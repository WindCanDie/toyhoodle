package com.toy.snipe.tcp.db;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransprtDBHandler extends ChannelInboundHandlerAdapter {
    TransprtDBClint transprtClint;

    public TransprtDBHandler() {
//        this.transprtClint = new TransprtDBClint();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
