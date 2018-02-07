package com.yw.databus.core;

import com.yw.databus.agent.AgentManager;
import com.yw.databus.core.rpc.RpcEndpoint;
import com.yw.databus.core.rpc.RpcEnv;
import com.yw.databus.thrift.AbstractThriftImpl;
import com.yw.databus.thrift.WorkerImpl;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thrift.iface.Service;
import thrift.iface.Worker;

import java.util.Map;

public class WorkerEndpoint extends RpcEndpoint {
    private Logger logger = LoggerFactory.getLogger(WorkerEndpoint.class);

    public static final String WORKER_NAME = "WORKER";
    private Config config;
    private TProcessor processor;
    private String serviceHost;
    private int servicepost;
    private AgentManager manager;

    public WorkerEndpoint(Config config) {
        this.config = config;
        this.processor = new Worker.Processor<Worker.Iface>(new WorkerImpl(manager));
    }

    @Override
    public void parper() throws Exception {
        this.serviceHost = config.getString("com.yw.databus.service.host");
        this.servicepost = config.getInt("com.yw.databus.service.post");
        Service.Iface serviceClient = (Service.Iface) RpcEnv.getClient(serviceHost, servicepost, ServerEndpoint.SERVER_NAME, Service.Client.class);
        boolean regist = serviceClient.regist(config.getString("ip"), RpcEnv.getPost(), WORKER_NAME);
        if (!regist) {
            logger.error("regist failure worker close");
        }
        Map<String, Map<String, String>> agentConf = serviceClient.agentConf(config.getString("ip"));

    }

    @Override
    public TProcessor getprocessor() {
        return processor;
    }

    @Override
    public void onStop() {

    }
}
