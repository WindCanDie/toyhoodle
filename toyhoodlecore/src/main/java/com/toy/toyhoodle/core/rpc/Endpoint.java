package com.toy.toyhoodle.core.rpc;

import java.util.function.Function;

/**
 * Created by ljx on 2017/1/3.
 */
public interface Endpoint {
    void receive(Function function);

    void onStart();

    void stop();
}
