package com.toy.common.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Administrator
 * on 2017/6/2.
 */
public class TransportClientFactory {
    private final TransportContext context;
    private final int timeoutMillis = 300000;
    private final int enventLoopThreadNum = 4;


    public TransportClientFactory(TransportContext transportContext) {
        context = transportContext;
    }

    public TransportClient createClient(String remoteHost, int remotePort) throws InterruptedException, IOException {
        InetSocketAddress address = new InetSocketAddress(remoteHost, remotePort);
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup(enventLoopThreadNum))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        //.option(ChannelOption.ALLOCATOR, pooledAllocator);

        final AtomicReference<SocketChannel> channel = new AtomicReference<>();
        final AtomicReference<TransportClient> channel1 = new AtomicReference<>();
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                TransportChannelHandler clientHandler = context.initializePipeline(ch);
                channel.set(ch);
                channel1.set(clientHandler.getClient());
            }
        });
        ChannelFuture cf = bootstrap.connect(address);

        if (!cf.await(timeoutMillis)) {
            throw new IOException(String.format("Connecting to %s timed out (%s ms)", address, timeoutMillis));
        } else if (cf.cause() != null) {
            throw new IOException(String.format("Failed to  connect to %s", address), cf.cause());
        }
        return channel1.get();
    }

}
