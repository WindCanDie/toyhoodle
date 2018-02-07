package com.toy.toytelephone.rpc;

/**
 * @author langjingxiang
 * @date 2017.05.31
 * nio server endpoint interface
 */
public interface RpcEndpoint {
    void onStart();

    Object receive(Object message);

    void receiveAndReply();

    void onConnected(RpcAddress remoteAddress);

    void onDisconnected(RpcAddress remoteAddress);

    void onNetworkError();

    void onStop();

    void stop();

    RpcEndpointRef self();
}
