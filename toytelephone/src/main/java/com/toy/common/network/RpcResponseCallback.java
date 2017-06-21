package com.toy.common.network;


import java.nio.ByteBuffer;

public interface RpcResponseCallback {
    void onSuccess(ByteBuffer response);

    void onFailure(Throwable e);
}
