package com.toy.caterpillar.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Administrator on 2017/8/11.
 */
public class Client {
	Bootstrap bootstrap;
	long t2 = System.currentTimeMillis();

    public void getClient(String ip, int post) throws InterruptedException {
        InetSocketAddress address = new InetSocketAddress(ip, post);
        if (bootstrap == null) {
            bootstrap = new Bootstrap()
                    .group(new NioEventLoopGroup(30))
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
            //.option(ChannelOption.ALLOCATOR, pooledAllocator);

            final AtomicReference<SocketChannel> channel = new AtomicReference<>();
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("decoder", new ObjectDecoder(1024 * 1024,
                            ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                    //添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
                    // channel.pipeline().addLast("serializable", new ObjectEncoder());
                    ch.pipeline().addLast("handler", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            System.out.println(ctx.channel().remoteAddress().toString());
                            ctx.close();
                        }

//                        @Override
//                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//                            System.out.println(ctx.channel().remoteAddress().toString());
//                            ctx.close();
//                        }
                    });
                    channel.set(ch);
                }

            });
        }
        ChannelFuture cf = bootstrap.connect(address);
    }

    @Test
    public void tcpClient() throws IOException, InterruptedException {
        for (int i = 1; i <= 65535; i++) {
            getClient("127.0.0.1", i);
        }
        while (true) {
        }
    }


}
