package com.toy.toytelephone.rpc;

/**
 * @author langjingxiang
 * @date 2017.05.31
 * nio server endpoint interface
 */
public interface RpcEndpoint {
    void onStart();

    void receive();

    void receiveAndReply();

    void onConnected();

    void onDisconnected();

    void onNetworkError();

    void onStop();

    void stop();

    RpcEndpointRef self();
}
