package com.toy.snipe.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyServerFactory {
    int enventLoopThreadNum = 4;
    ServerBootstrap bootstrap;

    public NettyServerFactory() {

    }

    public void createBootStrap() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(enventLoopThreadNum);
        EventLoopGroup workerGroup = new NioEventLoopGroup(enventLoopThreadNum);
        bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("handler", new TransprtHandler());
            }
        });

    }

    public void createTCPServer(String host, int post) {
        if (bootstrap == null)
            createBootStrap();
        InetSocketAddress address = host == null ?
                new InetSocketAddress(post) : new InetSocketAddress(host, post);
        bootstrap.bind(address).addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("sss");
                    } else {
                        System.out.println(future.cause().getMessage());
                    }
                }
        );
    }
}
