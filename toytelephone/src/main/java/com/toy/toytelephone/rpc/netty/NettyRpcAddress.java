package com.toy.toytelephone.rpc.netty;

import com.toy.toytelephone.rpc.RpcAddress;

/**
 * Created by Administrator on
 * 2017/7/20.
 */
public class NettyRpcAddress {
    public NettyRpcAddress(RpcAddress address, String name) {
        if (name == null)
            throw new RuntimeException("name is mast");
    }
    public NettyRpcAddress(String host, int post, String name) {
        if (name == null)
            throw new RuntimeException("name is mast");
    }
}
