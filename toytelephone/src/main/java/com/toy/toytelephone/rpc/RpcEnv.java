package com.toy.toytelephone.rpc;


import com.toy.toytelephone.rpc.netty.NettyRpcEnv;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public abstract class RpcEnv {
    protected int prot;
    protected String host;
    protected String name;

    protected RpcEnv(String name, String host, int prot) {
        this.prot = prot;
        this.host = host;
        this.name = name;
    }

    public static RpcEnv create(String name, String host, int prot) {
        return new NettyRpcEnv(name, host, prot).build();
    }

    public abstract RpcEndpointRef endpointRef(RpcEndpoint endpoint);

    public abstract void shutdown();

    /**
     * Register a {@link RpcEndpoint} with a name and return its {@link RpcEndpointRef}
     *
     * @param name
     * @param endpoint
     */
    public abstract RpcEndpointRef setupEndpoint(String name, RpcEndpoint endpoint);

    /**
     * uri search @link RpcEndpoint} and return its {@link RpcEndpointRef}
     *
     * @param uri
     * @return RpcEndpointRef
     */
    public abstract RpcEndpointRef setupEndpointRefByURI(String uri);

    public abstract RpcEndpointRef setupEndpointRefByURI(RpcAdress asress, String name);

    public abstract void stop(RpcEndpoint endpoint);


}
