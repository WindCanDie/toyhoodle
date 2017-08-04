package com.toy.tran.data;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/8/3.
 */
public abstract class Input {
    protected boolean finished = false;
    private boolean closed = false;
    private boolean gotNext = false;
    private Input nextValue;

    protected boolean haseNext() {
        return finished;
    }

    protected void closeIfNeeded() {
        if (!closed) {
            closed = true;
            close();
        }
    }

    abstract protected void close();

    abstract protected ByteBuffer getNext();
}
