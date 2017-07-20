package com.toy.toytelephone.rpc.netty;

import com.toy.toytelephone.rpc.RpcEndpointRef;

import static com.toy.util.JudgeUtil.require;

/**
 * Created by Administrator
 * on 2017/6/1.
 */
public class NettyRpcEndpointRef extends RpcEndpointRef {
    private NettyRpcEnv nettyEnv;
    private NettyRpcAddress nettyRpcAddress;

    public NettyRpcEndpointRef(NettyRpcEnv nettyEnv, NettyRpcAddress nettyRpcAddress) {
        this.nettyRpcAddress = nettyRpcAddress;
        this.nettyEnv = nettyEnv;
    }

    @Override
    public void send(Object message) {
        require(message != null, "Message is null");
        nettyEnv.send(new RequestMessage(nettyEnv.getAddress(), this, message));
    }

    @Override
    public Object ask(Object obj) {
        return null;
    }

    @Override
    public <T> T ask(Object obj, Class<T> clazz) {
        return null;
    }

    @Override
    public Object askSync(Object obj) {
        return null;
    }

    @Override
    public <T> T askSync(Object obj, Class<T> clazz) {
        return null;
    }
}
