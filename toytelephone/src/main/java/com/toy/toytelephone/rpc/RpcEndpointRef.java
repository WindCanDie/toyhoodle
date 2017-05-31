package com.toy.toytelephone.rpc;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public abstract class RpcEndpointRef {
    protected RpcAdress address;
    protected String name;

    public abstract void send(Object obj);

    public abstract Object ask(Object obj);

    public abstract <T> T ask(Object obj, Class<T> clazz);

    public abstract Object askSync(Object obj);

    public abstract <T> T askSync(Object obj, Class<T> clazz);

}
