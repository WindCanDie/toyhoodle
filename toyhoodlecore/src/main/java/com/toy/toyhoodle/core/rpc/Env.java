package com.toy.toyhoodle.core.rpc;

/**
 * Created by ljx on 2017/1/3.
 */
public interface Env {
    RefEndpoint setupEndpoint(String name, Endpoint endpoint);

    RefEndpoint setupEndpointRefByURI(String uri);

    RefEndpoint setupEndpointRef(String address, String endpointName);

    void stop(Endpoint endpoint);
}
