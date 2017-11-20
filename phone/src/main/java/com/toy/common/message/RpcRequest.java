package com.toy.common.message;

import com.toy.common.message.buffer.MessageBuffer;

/**
 * Created by Administrator on
 * 2017/6/16.
 */
public class RpcRequest implements RequestMessage {
    private final MessageBuffer messageBuffer;
    private final long requestId;

    public RpcRequest(long requestId, MessageBuffer messageBuffer) {
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
