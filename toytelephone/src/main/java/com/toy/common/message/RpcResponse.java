package com.toy.common.message;

import com.toy.common.message.buffer.MessageBuffer;

/**
 * Created by Administrator on
 * 2017/6/16.
 */
public class RpcResponse implements ResponseMessage {
    private final MessageBuffer messageBuffer;
    private final long requestId;

    public RpcResponse(long requestId, MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
        this.requestId = requestId;
    }

    public MessageBuffer body() {
        return messageBuffer;
    }

    public long getRequestId() {
        return requestId;
    }

}
