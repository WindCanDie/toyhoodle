package com.toy.tran.data.extract.file;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/8/4.
 */
public interface SerDe {
    ByteBuffer serialize();

    Object deserialize(ByteBuffer buffer);
}
