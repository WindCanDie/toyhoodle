package com.toy.common.message.buffer;

import java.io.IOException;
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
    /**
     * Exposes this buffer's data as an NIO ByteBuffer. Changing the position and limit of the
     * returned ByteBuffer should not affect the content of this buffer.
     */
    public abstract ByteBuffer nioByteBuffer() throws IOException;
    /**
     * If applicable, decrement the reference count by one and deallocates the buffer if the
     * reference count reaches zero.
     */
    public abstract MessageBuffer release();

}
