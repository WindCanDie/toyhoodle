package com.toy.common.network;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by Administrator on
 * 2017/6/2.
 */
public class TransportContext {

    private final RpcHandler rpcHandler;
    private final boolean closeIdleConnections;

    public TransportContext(RpcHandler rpcHandler) {
        this(rpcHandler, false);
    }

    public TransportContext(RpcHandler rpcHandler, boolean closeIdleConnections) {
        this.rpcHandler = rpcHandler;
        this.closeIdleConnections = closeIdleConnections;
    }

    public TransportChannelHandler initializePipeline(SocketChannel channel) {
        return initializePipeline(channel, rpcHandler);
    }

    public TransportChannelHandler initializePipeline(SocketChannel channel, RpcHandler rpcHandler) {
        TransportChannelHandler channelHandler = createChannelHandler(channel, rpcHandler);
        channel.pipeline().addLast("decoder", new ObjectDecoder(1024 * 1024,
                ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        //添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
       // channel.pipeline().addLast("serializable", new ObjectEncoder());
        channel.pipeline().addLast("handler", channelHandler);
        return channelHandler;
    }

    private TransportChannelHandler createChannelHandler(Channel channel, RpcHandler rpcHandler) {
        TransportResponseHandler responseHandler = new TransportResponseHandler(channel);
        TransportClient client = new TransportClient(channel, responseHandler);
        TransportRequestHandler requestHandler = new TransportRequestHandler(channel, client, rpcHandler);
        return new TransportChannelHandler(client, requestHandler, responseHandler, this.closeIdleConnections);
    }

}
