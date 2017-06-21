package com.toy.common.message.buffer;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/6/16.
 */
public abstract class MessageBuffer {
    protected ByteBuffer buffer;

    public MessageBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
}
