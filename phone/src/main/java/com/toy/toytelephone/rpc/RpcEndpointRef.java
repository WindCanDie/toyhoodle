package com.toy.toytelephone.rpc;

import java.util.concurrent.Future;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public abstract class RpcEndpointRef {
    protected RpcAddress address;
    protected String name;

    public abstract void send(Object obj);

    public abstract Future ask(Object obj);

    public abstract <T> T ask(Object obj, Class<T> clazz);

    public abstract Future askSync(Object obj);

    public abstract <T> T askSync(Object obj, Class<T> clazz);

}
