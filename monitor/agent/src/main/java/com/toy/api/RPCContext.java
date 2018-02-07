package com.toy.api;

import com.toy.toytelephone.rpc.RpcAddress;
import com.toy.toytelephone.rpc.RpcEndpointRef;
import com.toy.toytelephone.rpc.RpcEnv;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RPCContext {
    private RpcEnv rpcEnv;

    private RPCContext(String host, String post, String name) {
        rpcEnv = RpcEnv.create(host, name, Integer.parseInt(post));
    }

    public Object getActorRef(RpcAddress address) {
        RpcEndpointRef ref = rpcEnv.getEndpointRefByURI(address);
        Future handle = ref.askSync(new AbstractMessage.HandleMessage());
        Object proxy = null;
        try {
            String handleNmae = (String) handle.get(300, TimeUnit.SECONDS);
            proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    Class.forName(handleNmae).getInterfaces(), new RPCHandle(ref));

        } catch (InterruptedException | ExecutionException | TimeoutException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return proxy;
    }

    public void setUpActor(AbstractActor actor) {

    }

    public void setUpActor(Handle handle, String name) {
        setUpActor(new AbstractActor(handle) {
            @Override
            public String getName() {
                return name;
            }
        });
    }

    public static RPCContext create(String host, String post, String name) {
        return new RPCContext(host, post, name);
    }
}
