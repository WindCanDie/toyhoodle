package com.toy.snipe.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TransprtHandler extends ChannelInboundHandlerAdapter {
    Socket socket;

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("bbbbvvv");
        socket = new Socket("127.0.0.1", 3306);
        InputStream inputStream = socket.getInputStream();
        while (inputStream.available() <= 0) ;
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(inputStream, inputStream.available());
        ctx.writeAndFlush(byteBuf);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("vvvvv");
        ByteBuf message = (ByteBuf) msg;
        OutputStream out = socket.getOutputStream();
        byte[] b = new byte[message.capacity()];
        message.writeBytes(b);
        out.write(b);
        InputStream inputStream = socket.getInputStream();
        while (inputStream.available() <= 0) ;
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(inputStream, inputStream.available());
        ctx.writeAndFlush(byteBuf);
    }
}
