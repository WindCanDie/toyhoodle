package com.toy.toytelephone.rpc;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public class RpcEndpointNotFoundException extends Exception {
    public RpcEndpointNotFoundException() {
        super();
    }

    public RpcEndpointNotFoundException(String message) {
        super(message);
    }
}
