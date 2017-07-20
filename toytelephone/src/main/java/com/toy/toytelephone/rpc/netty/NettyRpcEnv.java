package com.toy.toytelephone.rpc.netty;

import com.toy.common.network.TransportClientFactory;
import com.toy.common.network.TransportContext;
import com.toy.common.network.TransportServer;
import com.toy.toytelephone.rpc.RpcAddress;
import com.toy.toytelephone.rpc.RpcEndpoint;
import com.toy.toytelephone.rpc.RpcEndpointRef;
import com.toy.toytelephone.rpc.RpcEnv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public class NettyRpcEnv extends RpcEnv {
    private Dispatcher dispatcher;
    private TransportContext transportContext;
    private TransportClientFactory clientFactory;
    private AtomicBoolean stopped;
    private ConcurrentHashMap<RpcAddress, Outbox> outboxes;
    private TransportServer server;
    private RpcAddress address;

    public NettyRpcEnv(String name, String host, int prot) {
        address = new RpcAddress(host, prot, name);
        init();
    }

    public RpcEnv create() {
        server = new TransportServer(address.getPort(), address.getHost(), transportContext);
        return this;
    }

    public RpcAddress getAddress() {
        return address;
    }

    public void init() {
        stopped = new AtomicBoolean(false);
        dispatcher = new Dispatcher(this);
        transportContext = new TransportContext(new NettyRpcHandler());
        clientFactory = new TransportClientFactory(transportContext);
        outboxes = new ConcurrentHashMap<>();
    }

    public void send(RequestMessage message) {
        RpcAddress remoteAddr = message.ReceiverAddress();
        if (remoteAddr == address) {
            //local
        } else {
            postToOutbox(message.Receiver(), new OneWayOutBoxMessage(message.serialize(this)));
        }
    }

    private void postToOutbox(NettyRpcEndpointRef receiver, OneWayOutBoxMessage oneWayOutBoxMessage) {

    }

    @Override
    public RpcEndpointRef endpointRef(RpcEndpoint endpoint) {
        return dispatcher.getRpcEndpointRef(endpoint);
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
