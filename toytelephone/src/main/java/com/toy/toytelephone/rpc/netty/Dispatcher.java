package com.toy.toytelephone.rpc.netty;

import com.toy.common.thread.ThreadUtil;
import com.toy.toytelephone.rpc.RpcEndpoint;

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
    private LinkedBlockingQueue<EndpointData> receivers = new LinkedBlockingQueue<>();
    private final EndpointData PoisonPill = new EndpointData(null, null, null);
    private NettyRpcEnv nettyEnv;

    public Dispatcher(NettyRpcEnv nettyRpcEnv) {
        this.nettyEnv = nettyRpcEnv;
        for (int i = 0; i < numThreads; i++) {
            threadPool.execute(new MessageLoop());
        }
    }


    public NettyRpcEndpointRef registerRpcEndpoint(String name, RpcEndpoint rpcEndpoint) {
        NettyRpcEndpointRef ref = new NettyRpcEndpointRef();
        if (endpoints.putIfAbsent(name, new EndpointData(name, rpcEndpoint, ref)) != null) {
            throw new IllegalArgumentException("This name is already exist");
        }
        EndpointData data = endpoints.get(name);
        receivers.offer(data);
        return ref;
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
