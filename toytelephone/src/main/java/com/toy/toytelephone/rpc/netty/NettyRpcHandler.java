package com.toy.toytelephone.rpc.netty;

import com.toy.common.network.RpcHandler;
import com.toy.common.network.RpcResponseCallback;
import com.toy.common.network.TransportClient;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/7/13.
 */
public class NettyRpcHandler implements RpcHandler {
    @Override
    public void receive(TransportClient client, ByteBuffer message, RpcResponseCallback callback) {

    }

    @Override
    public void receive(TransportClient client, ByteBuffer message) {

    }

    @Override
    public void channelActive(TransportClient client) {

    }

    @Override
    public void channelInactive(TransportClient client) {

    }

    @Override
    public void exceptionCaught(Throwable cause, TransportClient client) {

    }
}
