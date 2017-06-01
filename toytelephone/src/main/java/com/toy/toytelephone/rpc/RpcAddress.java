package com.toy.toytelephone.rpc;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author langjingxiang
 * @date 2017.05.31
 */
public class RpcAddress implements Serializable {
    private final String host;
    private final int port;
    private String toURL;

    public RpcAddress(String host, int post) {
        this.host = host;
        this.port = post;
        toURL = "toy://" + host + ":" + port;
    }

    public static RpcAddress fromURIString(String uri) throws URISyntaxException {
        URI uriObj = new java.net.URI(uri);
        return new RpcAddress(uriObj.getHost(), uriObj.getPort());
    }

    @Override
    public String toString() {
        return toURL;
    }
}
