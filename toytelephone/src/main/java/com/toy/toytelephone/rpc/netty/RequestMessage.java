package com.toy.toytelephone.rpc.netty;

import com.toy.toytelephone.rpc.RpcAddress;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/7/20.
 */
public class RequestMessage {
    public RequestMessage(RpcAddress address, NettyRpcEndpointRef endpointRef, Object message) {
    }

    public RpcAddress ReceiverAddress() {
        return null;
    }

    public NettyRpcEndpointRef Receiver() {
        return null;
    }

    public ByteBuffer serialize(NettyRpcEnv nettyRpcEnv) {
        return null;
    }

}
