package com.yw.databus.core.rpc;

import com.yw.databus.core.Config;
import com.yw.databus.util.ReflectUtil;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcEnv {
    private Logger logger = LoggerFactory.getLogger(RpcEnv.class);

    private static RpcEnv rpcEnv;
    private TNonblockingServerTransport serverTransport;
    private TMultiplexedProcessor processors;
    private Map<String, RpcEndpoint> registTable;
    private Map<String, String> thriftNamespaceT;
    private static int post;
    private Thread serverThread;
    private static Config config = new Config();

    public static int getPost() {
        return post;
    }

    private RpcEnv(int post, Config config) throws TTransportException {
        RpcEnv.config = config;
        RpcEnv.post = post;
        this.serverTransport = new TNonblockingServerSocket(post);
        this.processors = new TMultiplexedProcessor();
        this.registTable = new HashMap<>();
        this.thriftNamespaceT = new HashMap<>();
    }

    public static RpcEnv createRpcEvn(int post, Config config) throws TTransportException {
        if (rpcEnv == null)
            rpcEnv = new RpcEnv(post, config);
        return rpcEnv;
    }

    public static <T> Object getClient(String host, int post, String name, Class<T> clazz) {
        TTransport transport = new TFramedTransport(new TSocket(host, post, RpcConfigCentre.getSocketTimeout(config), RpcConfigCentre.getConnectTimeout(config)));
        TProtocol protocol = new TCompactProtocol(transport);
        TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, name);
        T client;
        Object proxy;
        try {
            Constructor<T> con = clazz.getConstructor(org.apache.thrift.protocol.TProtocol.class);
            client = con.newInstance(mp1);
            proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    clazz.getInterfaces(), new RpcSendHandle<>(transport, client));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return proxy;
    }

    public void regist(RpcEndpoint endpoint, String name) {
        String thriftNamespace = endpoint.getprocessor().getClass().getName().split("[$]")[0];
        processors.registerProcessor(name, endpoint.getprocessor());
        registTable.put(name, endpoint);
        thriftNamespaceT.put(name, thriftNamespace);
    }


    public void start() {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport)
                .processor(processors)
                .protocolFactory(new TCompactProtocol.Factory())
                .transportFactory(new TFramedTransport.Factory())
                .executorService(pool);

        final TThreadedSelectorServer server = new TThreadedSelectorServer(args);
        server.setServerEventHandler(new RpcEventHandler(registTable));

        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.serve();
            }
        });
        logger.info("post " + post + " service start");
        serverThread.start();
        examineRegist();
    }

    public void waitingForService() throws InterruptedException {
        serverThread.join();
    }

    @SuppressWarnings("unchecked")
    private void examineRegist() {
        for (Map.Entry<String, String> map : thriftNamespaceT.entrySet()) {
            boolean examineResult;
            try {
                Object client = getClient("localhost", post, map.getKey(), Class.forName(map.getValue() + "$Client"));
                examineResult = (boolean) ReflectUtil.methodInvok(client, "selfExamine");
            } catch (NoSuchMethodException e) {
                logger.error("thrift no method selfExamine" + e.getMessage());
                throw new RuntimeException(e);
            } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                logger.error("RpcEvn star failure " + e.getMessage());
                throw new RuntimeException(e);
            }

            if (examineResult) {
                logger.info("RpcEndpoint " + map.getKey() + " parper and examine success");
            } else {
                logger.info("RpcEndpoint " + map.getKey() + " parper and examine failure ");
                System.exit(1);
            }
        }
    }

    public synchronized void close() {
        for (Map.Entry<String, RpcEndpoint> map : registTable.entrySet()) {
            map.getValue().onStop();
        }
    }
}
