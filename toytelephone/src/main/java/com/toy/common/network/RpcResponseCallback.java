package com.toy.common.network;

/**
 * Created by Administrator on 2017/6/19.
 */

import java.nio.ByteBuffer;

public interface RpcResponseCallback {
    void onSuccess(ByteBuffer response);
    void onFailure(Throwable e);
}
