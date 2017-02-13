package com.toy.toyhoodle.core.rpc.impl;

/**
 * Created by ljx on 2017/1/4.
 *
 */
public class MessageObject {
    private String endpointName;
    private Object invok;
    private String post;
    private String host;

    public MessageObject(String endpointName, Object invok, String post, String host) {
        this.endpointName = endpointName;
        this.invok = invok;
        this.post = post;
        this.host = host;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public Object getInvok() {
        return invok;
    }

    public void setInvok(Object invok) {
        this.invok = invok;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
