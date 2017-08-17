package com.toy.snipe.tcp;

import com.toy.snipe.conf.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class NettyServerFactory {
    private static final String MODE_DATABASE = "database";
    private int enventLoopThreadNum;
    private Map<String, ServerBootstrap> bootstraps;
    private Config config;

    public NettyServerFactory(Config config) {
        this.config = config;
        this.bootstraps = new HashMap<>();
        init();
    }

    private void init() {
        enventLoopThreadNum = config.getIntConfig("enventThread", 4);
    }


    private ServerBootstrap createBootStrap(String type) {
        ServerBootstrap bootstrap = null;
        if (MODE_DATABASE.equals(type)) bootstrap = createDatabase();
        bootstraps.put(MODE_DATABASE, bootstrap);
        return bootstrap;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public void createTCPServer(Map map) {
        ServerBootstrap bootstrap = bootstraps.get(map.get("mode"));
        if (bootstrap == null)
            bootstrap = createBootStrap((String) map.get("mode"));
        int post = Integer.parseInt((String) map.get("post"));
        InetSocketAddress address = new InetSocketAddress(post);
        try {
            bootstrap.bind(address).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException("server run Exception" + e.getMessage());
        }
    }

    private ServerBootstrap createDatabase() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(enventLoopThreadNum);
        EventLoopGroup workerGroup = new NioEventLoopGroup(enventLoopThreadNum);
        ServerBootstrap bootstrap = new ServerBootstrap()
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
        return bootstrap;
    }

}
