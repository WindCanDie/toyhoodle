package com.yw.databus.core;

import com.yw.databus.core.rpc.RpcEnv;
import org.apache.thrift.transport.TTransportException;

public class Appaction {
    public static final String ROLE_WORKER = "worker";
    public static final String ROLE_SERVER = "server";

    public static void main(String[] args) throws TTransportException, InterruptedException {
        int post = Integer.parseInt(args[0]);
        String role = args[1];
        String confPath = args[2];
        Config config = new Config();
        config.loadFileConfig(confPath);
        final RpcEnv rpcEnv = RpcEnv.createRpcEvn(post, config);
        if (ROLE_SERVER.equals(role)) {

        } else if (ROLE_WORKER.equals(role)) {
            rpcEnv.regist(new WorkerEndpoint(config), WorkerEndpoint.WORKER_NAME);
        } else {
            throw new RuntimeException("Role error worker or server");
        }
        rpcEnv.start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                rpcEnv.close();
            }
        }));
        rpcEnv.waitingForService();
    }


}
