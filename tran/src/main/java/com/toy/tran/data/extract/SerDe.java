package com.toy.tran.data.extract;

import java.io.DataInputStream;
import java.nio.ByteBuffer;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/8/4.
 */
public interface SerDe {
    void initialize(Properties tbl);

    ByteBuffer serialize(Object obj);

    Object deserialize(DataInputStream buffer);
}
