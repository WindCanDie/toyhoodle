package com.toy.jdbc.test;

import com.toy.snipe.Start;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ljx on
 * 2017/8/3.
 */
public class StartTest {
    @Test
    public void starTest0() throws FileNotFoundException, InterruptedException {
        String[] arge = {"D:\\work\\ljx\\snipe\\src\\main\\resources\\hoodle.yml"};
        Start.main(arge);
    }
    public static void main(String[] args) {
        ServerBootstrap bootstrap;
        int enventLoopThreadNum = 4;
        EventLoopGroup bossGroup = new NioEventLoopGroup(enventLoopThreadNum);
        EventLoopGroup workerGroup = new NioEventLoopGroup(enventLoopThreadNum);
        bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    Socket socket;

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channelRegistered");
                        ctx.fireChannelRegistered();
                    }

                    @Override
                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channelUnregistered");
                        ctx.fireChannelUnregistered();
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        // System.out.println("channelActive");
                        ctx.fireChannelActive();
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channelInactive");
                        ctx.fireChannelInactive();
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println(ctx.channel().remoteAddress().toString() + "channelRead");
                        ByteBuf byteBuf = (ByteBuf) msg;
                        byte[] bytes = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(bytes);
                        if (this.socket == null) {
                            this.socket = new Socket("SFHN07", 21050);
                            //socket.connect(new InetSocketAddress("SFHN07", 21050));
                        }

                        OutputStream out = socket.getOutputStream();
                        out.write(bytes);
                        InputStream input = socket.getInputStream();
                        for (; input.available() == 0; ) ;

                        ByteBuf byteBuf1 = ctx.alloc().buffer(4);
                        try {
                            byteBuf1.writeBytes(input, input.available());
                        } catch (Exception e) {
                            System.out.print(e);
                        }
                        System.out.println(byteBuf1);

                        final ChannelFuture f = ctx.writeAndFlush(byteBuf1);
                        f.addListener((ChannelFutureListener) future -> {
                            if (future.isSuccess()) {
                                System.out.println("aaaa");
                            }
                        });


//
//                        ByteBuf byteBuf1 = ctx.alloc().buffer(4);
//                        byteBuf1.writeByte('c');
//                        byteBuf1.writeByte('c');
//                        final ChannelFuture f = ctx.writeAndFlush(byteBuf1);
//                        f.addListener((ChannelFutureListener) future -> {
//                            if (future.isSuccess()) {
//                                System.out.println("aaaa");
//                            }
//                        });

                    }

                    @Override
                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                        // System.out.println("channelReadComplete");
                        ctx.fireChannelReadComplete();
                    }

                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                        System.out.println("userEventTriggered");
                        ctx.fireUserEventTriggered(evt);
                    }

                    @Override
                    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("channelWritabilityChanged");
                        ctx.fireChannelWritabilityChanged();
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                            throws Exception {
                        System.out.println("exceptionCaught");
                        ctx.fireExceptionCaught(cause);
                    }
                });
            }
        });

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 10001);
        ChannelFuture channelFuture = bootstrap.bind(address);
        channelFuture.syncUninterruptibly();

        while (true) {
        }
    }

    @Test
    public void starTest() throws FileNotFoundException, InterruptedException {
        String[] arge = {"G:\\java\\toy\\toyhoodle\\snipe\\src\\main\\resources\\hoodle.yml"};
        Start.main(arge);
    }


}
