package com.toy.snipe.tcp.db;

import com.toy.snipe.tcp.Scheduler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on
 * 2017/8/21.
 */
public class TransprtDBClint0 {
    private Scheduler scheduler;
    private ConcurrentMap<String, Client> cliMap;
    ConcurrentMap<String, Client> answerMap;
    private Bootstrap bootstrap;

    public TransprtDBClint0(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.cliMap = new ConcurrentHashMap<>();
        this.answerMap = new ConcurrentHashMap<>();
        this.bootstrap = createBootClient();
    }

    private Bootstrap createBootClient() {
        bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup(30))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true);
        ClientHandle handle = new ClientHandle(this);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(handle);
            }
        });
        return bootstrap;
    }

     static class Client {
        ChannelHandlerContext ctx;
        Channel channel;

        Client(ChannelHandlerContext ctx, Channel channel) {
            this.ctx = ctx;
            this.channel = channel;
        }


        void sendData(ByteBuf byteBuf) throws IOException {
            channel.writeAndFlush(byteBuf);
        }

        void dataWriteBack(ByteBuf byteBuf) throws IOException {
            ctx.writeAndFlush(byteBuf);
        }

        void close() throws IOException {
            channel.close();
        }

    }
    void wride(ChannelHandlerContext ctx, final ByteBuf byteBuf) throws IOException {
        Client client = cliMap.get(getRemoteId(ctx.channel()));
        client.sendData(byteBuf);
    }

    void createClient(ChannelHandlerContext ctx) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(scheduler.getServerAddress()).sync();
        Client client = new Client(ctx, future.channel());
        cliMap.put(getRemoteId(ctx.channel()), client);
        answerMap.put(getLocalId(future.channel()), client);
    }

     String getLocalId(Channel channel) {
        InetSocketAddress address = (InetSocketAddress) channel.localAddress();
        return address.getHostName() + address.getPort();
    }

    private String getRemoteId(Channel channel) {
        InetSocketAddress address = (InetSocketAddress) channel.localAddress();
        return address.getHostName() + address.getPort();
    }

    void closeClient(ChannelHandlerContext ctx) throws IOException {
        Client client = cliMap.get(getRemoteId(ctx.channel()));
        client.close();
        cliMap.remove(getRemoteId(ctx.channel()));
        answerMap.remove(getLocalId(client.channel));
    }

    public void close() throws IOException {
    }
}
@ChannelHandler.Sharable
class ClientHandle extends ChannelInboundHandlerAdapter {
    private TransprtDBClint0 transprtDBClint0;
    ClientHandle(TransprtDBClint0 transprtDBClint0){
        this.transprtDBClint0 = transprtDBClint0;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TransprtDBClint0.Client client = transprtDBClint0.answerMap.get(transprtDBClint0.getLocalId(ctx.channel()));
        client.dataWriteBack((ByteBuf) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
