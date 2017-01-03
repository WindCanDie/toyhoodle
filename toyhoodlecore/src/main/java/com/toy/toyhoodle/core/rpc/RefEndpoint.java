package com.toy.toyhoodle.core.rpc;

/**
 * Created by ljx on 2017/1/3.
 */
public interface RefEndpoint {
    void send(Message message);

    Future ask(Message message);

    Future ask(Message message, int time);

    Object askWithRetry(Message message);

    Object askWithRetry(Message message, int time);
}
