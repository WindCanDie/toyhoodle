package com.toy.api;

import com.toy.toytelephone.rpc.RpcAddress;
import com.toy.toytelephone.rpc.RpcEndpoint;
import com.toy.toytelephone.rpc.RpcEndpointRef;

public abstract class AbstractActor implements RpcEndpoint {
    private Handle handle;

    public AbstractActor(Handle handle) {
        this.handle = handle;
    }

    public abstract String getName();

    @Override
    public void onStart() {

    }

    @Override
    public Object receive(Object message) {
        if (message instanceof AbstractMessage.HandleMessage) {
            return handle.getClass().getName();
        } else if (message instanceof AbstractMessage.RemoteMessage) {
            try {
                return ((AbstractMessage.RemoteMessage) message).method.invoke(handle, ((AbstractMessage.RemoteMessage) message).args);
            } catch (Exception e) {
                return e.toString();
            }
        }
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
