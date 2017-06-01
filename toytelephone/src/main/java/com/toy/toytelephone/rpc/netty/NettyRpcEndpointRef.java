package com.toy.toytelephone.rpc.netty;

import com.toy.toytelephone.rpc.RpcEndpointRef;

/**
 * Created by Administrator
 * on 2017/6/1.
 */
public class NettyRpcEndpointRef extends RpcEndpointRef {
    @Override
    public void send(Object obj) {

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
