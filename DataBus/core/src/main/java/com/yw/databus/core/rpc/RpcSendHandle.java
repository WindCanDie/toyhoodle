package com.yw.databus.core.rpc;

import org.apache.thrift.transport.TTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcSendHandle<T> implements InvocationHandler {
    private TTransport tTransport;
    private T client;

    public RpcSendHandle(TTransport transport, T client) {
        this.tTransport = transport;
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        tTransport.open();
        Object object;
        try {
            object = method.invoke(client, args);
        } finally {
            tTransport.close();
        }
        return object;
    }
}
