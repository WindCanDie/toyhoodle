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

    public TransportChannelHandler initializePipeline(SocketChannel channel) {
        TransportChannelHandler channelHandler = createChannelHandler(channel);
        channel.pipeline().addLast("decoder", new ObjectDecoder(1024 * 1024,
                ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        //添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
        channel.pipeline().addLast("serializable", new ObjectEncoder());
        channel.pipeline().addLast("handler", channelHandler);
        return channelHandler;
    }

    private TransportChannelHandler createChannelHandler(Channel channel) {
        TransportResponseHandler responseHandler = new TransportResponseHandler(channel);
        TransportClient client = new TransportClient(channel, responseHandler);
        TransportRequestHandler requestHandler = new TransportRequestHandler(channel, client);
        return new TransportChannelHandler(client, responseHandler, requestHandler);
    }

}
