package com.toy.tran.data.extract.file;

import com.toy.tran.data.extract.Extract;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/7/17.
 */
public class FileExtract extends Extract {
    private InputStream inputStream;

    public FileExtract(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    protected void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ByteBuffer getNext() {
        return null;
    }
}
