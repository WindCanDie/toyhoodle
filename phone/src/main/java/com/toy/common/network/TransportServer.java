package com.toy.common.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on
 * 2017/6/6.
 */
public class TransportServer {
    private final TransportContext context;
    private ServerBootstrap bootstrap;
    private final int enventLoopThreadNum = 4;
    private ChannelFuture channelFuture;

    public TransportServer(int post, String host, TransportContext context) {
        this.context = context;
        init(post, host);
    }

    private void init(int post, String host) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(enventLoopThreadNum);
        EventLoopGroup workerGroup = new NioEventLoopGroup(enventLoopThreadNum);
        bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                context.initializePipeline(ch);
            }
        });

        InetSocketAddress address = host == null ?
                new InetSocketAddress(post) : new InetSocketAddress(host, post);
        channelFuture = bootstrap.bind(address);
        channelFuture.syncUninterruptibly();

        int porta = ((InetSocketAddress) channelFuture.channel().localAddress()).getPort();

    }

}
