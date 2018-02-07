package com.toy.core;

import com.toy.toytelephone.rpc.RpcAddress;
import com.toy.toytelephone.rpc.RpcEndpoint;
import com.toy.toytelephone.rpc.RpcEndpointRef;
import com.toy.toytelephone.rpc.RpcEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Worker implements RpcEndpoint {
    Logger log = LoggerFactory.getLogger(Worker.class);
    public static final String WORKER_NAME = "worker";
    public static final String WORKER_EVN = "worker_evn";

    private RpcAddress workerRpcAddress;

    private RpcEnv rpcEnv;
    private RpcAddress masterRpcAddress;
    private RpcEndpointRef masterRef;
    private Configuration conf;

    private int timeout;

    public Worker(RpcEnv rpcEnv, RpcAddress masterRpcAddress, RpcAddress workerRpcAddress, Configuration conf) {
        this.conf = conf;
        this.rpcEnv = rpcEnv;
        this.masterRpcAddress = masterRpcAddress;
        this.workerRpcAddress = workerRpcAddress;
        init();
    }

    private void init() {
        timeout = (int) conf.getOrDefault("toy.worker.timeout", 30);
        masterRef = rpcEnv.getEndpointRefByURI(masterRpcAddress);
    }

    @Override
    public void onStart() {
        if (!register()) rpcEnv.stop(this);


    }

    @Override
    public Object receive(Object message) {
        return null;
    }

    @Override
    public void receiveAndReply() {

    }

    @Override
    public void onConnected(RpcAddress remoteAddress) {

    }


    @Override
    public void onDisconnected(RpcAddress remoteAddress) {

    }


    @Override
    public void onNetworkError() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public RpcEndpointRef self() {
        return null;
    }

    private boolean register() {
        Future regFuture = masterRef.ask(new SreverMessage.Register(workerRpcAddress, WORKER_NAME));
        try {
            WorkerMessage.RegisterSuccess re = (WorkerMessage.RegisterSuccess) regFuture.get(timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String post = args[0];
        String host = args[1];
        String masterPost = args[2];
        String masterHost = args[3];

        Configuration conf = new Configuration();
        RpcAddress serverRpcAddress = new RpcAddress(masterHost, Integer.parseInt(masterPost), Server.SERVER_NAME);
        RpcAddress workerRpcAddress = new RpcAddress(host, Integer.parseInt(post), WORKER_NAME);
        RpcEnv rpcEnv = RpcEnv.create(WORKER_EVN, post, Integer.parseInt(host));

        rpcEnv.setupEndpoint(WORKER_NAME, new Worker(rpcEnv, serverRpcAddress, workerRpcAddress, conf));
    }
}
