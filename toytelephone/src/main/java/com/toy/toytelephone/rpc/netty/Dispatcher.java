package com.toy.toytelephone.rpc.netty;

import com.toy.thread.ThreadUtil;
import com.toy.toytelephone.rpc.RpcEndpoint;
import com.toy.toytelephone.rpc.RpcEndpointRef;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by Administrator on
 * 2017/6/1.
 */
public class Dispatcher {
    private int numThreads = 4;
    private ThreadPoolExecutor threadPool = ThreadUtil.newDaemonFixedThreadPool(numThreads, "dispatcher-event-loop");
    private ConcurrentMap<String, EndpointData> endpoints = new ConcurrentHashMap<>();
    ConcurrentMap<RpcEndpoint, RpcEndpointRef> endpointRefs = new ConcurrentHashMap<>();
    private LinkedBlockingQueue<EndpointData> receivers = new LinkedBlockingQueue<>();
    private final EndpointData PoisonPill = new EndpointData(null, null, null);
    private NettyRpcEnv nettyEnv;
    private boolean stopped = false;

    public Dispatcher(NettyRpcEnv nettyRpcEnv) {
        this.nettyEnv = nettyRpcEnv;
        for (int i = 0; i < numThreads; i++) {
            threadPool.execute(new MessageLoop());
        }
    }


    public NettyRpcEndpointRef registerRpcEndpoint(String name, RpcEndpoint rpcEndpoint) {
        NettyRpcAddress nettyRpcAddress = new NettyRpcAddress(nettyEnv.getAddress(), name);
        NettyRpcEndpointRef ref = new NettyRpcEndpointRef(nettyEnv, nettyRpcAddress);
        if (endpoints.putIfAbsent(name, new EndpointData(name, rpcEndpoint, ref)) != null) {
            throw new IllegalArgumentException("This name is already exist");
        }
        EndpointData data = endpoints.get(name);
        endpointRefs.put(data.getRpcEndpoint(), data.getRpcEndpointRef());
        receivers.offer(data);
        return ref;
    }

    public RpcEndpointRef getRpcEndpointRef(RpcEndpoint endpoint) {
        return endpointRefs.get(endpoint);
    }

    private class MessageLoop implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    EndpointData data = receivers.take();
                    if (data == PoisonPill) {
                        receivers.offer(PoisonPill);
                        return;
                    }
                    data.inbox.process(Dispatcher.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void postOneWayMessage(RequestMessage message) {

    }

    private void postMessage(String endpointName, InboxMessage message) {
        EndpointData data = endpoints.get(endpointName);
        if (stopped) {
            throw new RuntimeException("Env hased stop");
        } else if (data == null) {
            throw new RuntimeException("Could not find " + endpointName);
        } else {
            data.inbox.post(message);
            receivers.offer(data);
        }
    }

    private static final class EndpointData {
        private String name;
        private RpcEndpoint rpcEndpoint;
        private NettyRpcEndpointRef rpcEndpointRef;
        Inbox inbox;

        EndpointData(String name, RpcEndpoint rpcEndpoint, NettyRpcEndpointRef rpcEndpointRef) {
            this.name = name;
            this.rpcEndpoint = rpcEndpoint;
            this.rpcEndpointRef = rpcEndpointRef;
        }

        public String getName() {
            return name;
        }

        public RpcEndpoint getRpcEndpoint() {
            return rpcEndpoint;
        }

        public NettyRpcEndpointRef getRpcEndpointRef() {
            return rpcEndpointRef;
        }
    }
}
