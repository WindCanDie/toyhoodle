package com.yw.databus.thrift;

import com.yw.databus.agent.AgentManager;
import com.yw.databus.core.rpc.RpcEndpoint;
import org.apache.thrift.TException;
import thrift.iface.Worker;

public class WorkerImpl extends AbstractThriftImpl implements Worker.Iface {

    public WorkerImpl(AgentManager manager) {
    }

    @Override
    public String sayHello(String username) throws TException {
        return null;
    }
}
