package com.yw.databus.core.rpc;

import org.apache.thrift.TProcessor;

public abstract class RpcEndpoint {

    public abstract void parper() throws Exception;

    public abstract TProcessor getprocessor();

    public abstract void onStop();


}
