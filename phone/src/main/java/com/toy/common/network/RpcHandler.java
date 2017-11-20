package com.toy.common.network;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/6/23.
 */
public interface RpcHandler {

    /**
     * Returns the StreamManager which contains the state about which streams are currently being
     * fetched by a TransportClient.
     */
    //public  StreamManager getStreamManager();
     void receive(TransportClient client, ByteBuffer message, RpcResponseCallback callback);

    void receive(TransportClient client, ByteBuffer message);

    void channelActive(TransportClient client);

    void channelInactive(TransportClient client);

    void exceptionCaught(Throwable cause, TransportClient client);

}
