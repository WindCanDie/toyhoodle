package com.yw.databus.thrift;

import com.yw.databus.core.rpc.RpcEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractThriftImpl {
    private Logger logger = LoggerFactory.getLogger(AbstractThriftImpl.class);

    public boolean selfExamine() {
        return true;
    }
}
