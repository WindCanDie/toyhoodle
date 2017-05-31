package com.toy.toytelephone.rpc.netty;

import com.toy.toytelephone.rpc.RpcAdress;
import com.toy.toytelephone.rpc.RpcEndpoint;
import com.toy.toytelephone.rpc.RpcEndpointRef;
import com.toy.toytelephone.rpc.RpcEnv;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public class NettyRpcEnv extends RpcEnv {

    public NettyRpcEnv(String name, String host, int prot) {
        super(host, name, prot);
    }

    public RpcEnv build() {
        return null;
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
        return null;
    }

    @Override
    public RpcEndpointRef setupEndpointRefByURI(String uri) {
        return null;
    }

    @Override
    public RpcEndpointRef setupEndpointRefByURI(RpcAdress asress, String name) {
        return null;
    }

    @Override
    public void stop(RpcEndpoint endpoint) {

    }
}
