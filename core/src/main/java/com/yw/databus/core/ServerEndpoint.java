package com.yw.databus.core;

import com.yw.databus.core.rpc.RpcEndpoint;
import com.yw.databus.thrift.ServiceImpl;
import org.apache.thrift.TProcessor;
import thrift.iface.Service;

public class ServerEndpoint extends RpcEndpoint {
    public static final String SERVER_NAME = "SERVER";
    private Service.Processor processor;

    public ServerEndpoint() {
        this.processor = new Service.Processor<Service.Iface>(new ServiceImpl());
    }

    @Override
    public void parper() {
        System.out.println("parper");
    }

    @Override
    public TProcessor getprocessor() {
        return processor;
    }

    @Override
    public void onStop() {

    }

}
