package com.toy.core;

import com.toy.toytelephone.rpc.RpcAddress;
import com.toy.toytelephone.rpc.RpcEndpoint;
import com.toy.toytelephone.rpc.RpcEndpointRef;

public class Server implements RpcEndpoint {
    public static final String SERVER_NAME = "server";
    public static final String SERVER_EVN = "server_evn";

    @Override
    public void onStart() {

    }

    @Override
    public Object receive(Object message) {
        return null;
    }

    @Override
    public void receiveAndReply() {

    }

    @Override
    public void onConnected(RpcAddress remoteAddress) {

    }


    @Override
    public void onDisconnected(RpcAddress remoteAddress) {

    }


    @Override
    public void onNetworkError() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void stop() {

    }

    @Override
    public RpcEndpointRef self() {
        return null;
    }
}
