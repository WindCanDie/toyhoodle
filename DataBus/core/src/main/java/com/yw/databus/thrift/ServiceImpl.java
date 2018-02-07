package com.yw.databus.thrift;

import org.apache.thrift.TException;
import thrift.iface.Service;

import java.util.Map;

public class ServiceImpl extends AbstractThriftImpl implements Service.Iface {
    public ServiceImpl() {
    }

    @Override
    public boolean regist(String host, int post, String name) throws TException {
        return false;
    }

    @Override
    public Map<String, Map<String, String>> agentConf(String id) throws TException {
        return null;
    }
}
