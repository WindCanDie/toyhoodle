package com.toy.common.message;

import com.toy.common.message.buffer.MessageBuffer;

/**
 * Created by Administrator on
 * 2017/6/16.
 */
public class RpcRequest implements RequestMessage {
    MessageBuffer messageBuffer;

    public RpcRequest(MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }
}
