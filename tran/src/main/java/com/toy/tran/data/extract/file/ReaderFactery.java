package com.toy.tran.data.extract.file;

import com.toy.util.ClassUtil;

import java.util.function.Supplier;

/**
 * Created by Administrator on
 * 2017/8/10.
 */
public class ReaderFactery {
    private Supplier<DataFileReader> supplier;

    private ReaderFactery(Supplier<DataFileReader> supplier) {
        this.supplier = supplier;
    }

    public DataFileReader newFileReader() {
        return supplier.get();
    }

    public static ReaderFactery build(String className) {
        return new ReaderFactery(() -> (DataFileReader) ClassUtil.getObject(className)
        );
    }
}
