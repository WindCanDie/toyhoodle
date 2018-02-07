package com.toy.api;

import com.toy.toytelephone.rpc.RpcEndpointRef;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RPCHandle implements InvocationHandler {
    RpcEndpointRef ref;

    public RPCHandle(RpcEndpointRef ref) {
        this.ref = ref;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Future r = ref.askSync(new AbstractMessage.RemoteMessage(proxy, method, args));
        return r.get(30, TimeUnit.SECONDS);
    }
}
