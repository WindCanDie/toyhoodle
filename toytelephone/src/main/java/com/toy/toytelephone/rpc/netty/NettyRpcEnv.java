package com.toy.toytelephone.rpc.netty;

import com.toy.toytelephone.rpc.RpcAddress;
import com.toy.toytelephone.rpc.RpcEndpoint;
import com.toy.toytelephone.rpc.RpcEndpointRef;
import com.toy.toytelephone.rpc.RpcEnv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public class NettyRpcEnv extends RpcEnv {
    private Dispatcher dispatcher;


    public NettyRpcEnv(String name, String host, int prot) {
        super(host, name, prot);
        init();
    }

    public void init() {
        dispatcher = new Dispatcher(this);
    }

    @Override
    public RpcEndpointRef endpointRef(RpcEndpoint endpoint) {
        return null;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public RpcEndpointRef setupEndpoint(String name, RpcEndpoint endpoint) {
        return dispatcher.registerRpcEndpoint(name, endpoint);
    }

    @Override
    public RpcEndpointRef setupEndpointRefByURI(String uri) {
        return null;
    }

    @Override
    public RpcEndpointRef setupEndpointRefByURI(RpcAddress asress, String name) {
        return null;
    }

    @Override
    public void stop(RpcEndpoint endpoint) {

    }
}
