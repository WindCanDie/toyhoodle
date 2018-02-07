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
    private String name;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public RpcAddress(String host, int post, String name) {
        this.host = host;
        this.port = post;
        this.name = name;
        toURL = "toy://" + name + "@" + host + ":" + port;
    }

    public static RpcAddress fromURIString(String uri) throws URISyntaxException {
        URI uriObj = new java.net.URI(uri);
        return new RpcAddress(uriObj.getHost(), uriObj.getPort(), uriObj.getQuery());
    }

    @Override
    public String toString() {
        return toURL;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RpcAddress)
            return toURL.equals(obj.toString());
        else
            return false;
    }
}
