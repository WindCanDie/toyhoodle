package com.toy.tran.data.extract.file;

import java.io.Closeable;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/8/10.
 */
interface DataFileReader extends Closeable {
    /**
     * @return file dir
     */
    String getDataDir();

    /**
     * @return Readr one data to  file end return "null"
     */
    ByteBuffer next();
}
