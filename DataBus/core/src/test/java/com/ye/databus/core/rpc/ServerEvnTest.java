package com.ye.databus.core.rpc;

import com.yw.databus.core.Config;
import com.yw.databus.core.ServerEndpoint;
import com.yw.databus.core.WorkerEndpoint;
import com.yw.databus.core.rpc.RpcEnv;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;
import thrift.iface.Service;
import thrift.iface.Worker;

import java.util.Map;

public class ServerEvnTest {

    @Test
    public void createRpcEvnTest() throws TTransportException {
        RpcEnv evn = RpcEnv.createRpcEvn(33334, new Config());
        evn.regist(new ServerEndpoint(), ServerEndpoint.SERVER_NAME);
        evn.regist(new ServerEndpoint(), "220");
        evn.start();
    }

    @Test
    public void getClientTest() throws TException {
        Service.Iface client = (Service.Iface) RpcEnv.getClient("127.0.0.1", 33334, ServerEndpoint.SERVER_NAME, Service.Client.class);
        client.regist("aa", 123, "adsfa");
        Service.Iface client2 = (Service.Iface) RpcEnv.getClient("127.0.0.1", 33334, ServerEndpoint.SERVER_NAME, Service.Client.class);
        client2.regist("aa", 123, "adsfa");
    }

    @Test
    public void createRpcEvnWorkerTest() throws TTransportException, InterruptedException {
        RpcEnv evn = RpcEnv.createRpcEvn(33334, new Config());
        evn.regist(new WorkerEndpoint(new Config()), WorkerEndpoint.WORKER_NAME);
        evn.start();
        evn.waitingForService();
    }

    @Test
    public void threadSafety() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Worker.Iface client = (Worker.Iface) RpcEnv.getClient("127.0.0.1", 33334, WorkerEndpoint.WORKER_NAME, Worker.Client.class);
                        String a = client.sayHello("11111");
                        System.out.println(a);
                    } catch (TException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(10000);
    }


}

class ServiceImpl implements Service.Iface {

    @Override
    public boolean selfExamine() throws TException {
        return false;
    }

    @Override
    public boolean regist(String host, int post, String name) throws TException {
        System.out.println(1);
        return false;
    }

    @Override
    public Map<String, Map<String, String>> agentConf(String id) throws TException {
        return null;
    }
}

