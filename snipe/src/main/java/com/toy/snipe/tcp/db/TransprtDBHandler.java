package com.toy.snipe.tcp.db;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransprtDBHandler extends ChannelInboundHandlerAdapter {
    private TransprtDBClint transprtClint;

    public TransprtDBHandler(TransprtDBClint transprtClint) {
        this.transprtClint = transprtClint;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        transprtClint.createClient(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        transprtClint.wride(ctx, (ByteBuf) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        transprtClint.closeClient(ctx);
        ctx.close();
    }


}
