package com.toy.toytelephone.rpc;

/**
 * Created by Administrator
 * on 2017/6/1.
 */
public interface RpcCallContext {
    void reply(Object object);

    void sendFailure(Exception e);

    RpcAddress senderAddress();

}
