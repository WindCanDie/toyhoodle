package com.toy.api;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface AbstractMessage extends Serializable {
    class HandleMessage implements AbstractMessage {
    }

    class RemoteMessage implements AbstractMessage {
        Object proxy;
        Method method;
        Object[] args;

        RemoteMessage(Object proxy, Method method, Object[] args) {
            this.proxy = proxy;
            this.method = method;
            this.args = args;
        }
    }
}
