package com.toy.tran.data;

import java.io.Closeable;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/8/3.
 */
public interface Input extends Closeable {
    /**
     * @return Readr one data to  file end return "null"
     */
    ByteBuffer next();
}
