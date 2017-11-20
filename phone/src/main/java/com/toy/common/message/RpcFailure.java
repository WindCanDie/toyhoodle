package com.toy.common.message;

/**
 * Created by Administrator on
 * 2017/6/16.
 */
public class RpcFailure implements ResponseMessage {
    public final long requestId;
    public final String errorString;

    public RpcFailure(long requestId , String errorString) {
        this.requestId = requestId;
        this.errorString = errorString;
    }

    public long getRequestId() {
        return requestId;
    }

    public String getErrorString() {
        return errorString;
    }
}
