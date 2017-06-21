package com.toy.common.message.buffer;

import java.nio.ByteBuffer;

/**
 * Created by Administrator
 * on 2017/6/16.
 */
public class NioMessageBuffer extends MessageBuffer {
    public NioMessageBuffer(ByteBuffer buf) {
        super(buf);
    }

    public long size() {
        return super.buffer.remaining();
    }
}
