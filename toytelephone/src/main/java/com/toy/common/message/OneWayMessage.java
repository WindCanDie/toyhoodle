package com.toy.common.message;

import com.toy.common.message.buffer.MessageBuffer;

/**
 * Created by Administrator on
 * 2017/6/16.
 */
public class OneWayMessage implements RequestMessage {
    MessageBuffer messageBuffer;

    public OneWayMessage(MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }

    public MessageBuffer body() {
        return messageBuffer;
    }
}
