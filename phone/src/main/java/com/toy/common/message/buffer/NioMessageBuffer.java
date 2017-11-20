package com.toy.common.message.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Administrator
 * on 2017/6/16.
 */
public class NioMessageBuffer extends MessageBuffer {
    public NioMessageBuffer(ByteBuffer buf) {
        super(buf);
    }

    @Override
    public ByteBuffer nioByteBuffer() throws IOException {
        return super.buffer.duplicate();
    }

    @Override
    public MessageBuffer release() {
        return this;
    }

    public long size() {
        return super.buffer.remaining();
    }
}
