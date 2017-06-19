package com.toy.common.message.buffer;

import java.nio.ByteBuffer;

/**
 * Created by Administrator
 * on 2017/6/16.
 */
public class NioMessageBuffer extends MessageBuffer {
    private final ByteBuffer buf;

    public NioMessageBuffer(ByteBuffer buf) {
        this.buf = buf;
    }

    public long size() {
        return buf.remaining();
    }
}
