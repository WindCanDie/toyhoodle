package com.toy.tarn.test;

import com.toy.tran.data.extract.file.LineInputStream;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/8/4.
 */
public class LineInputStreamTest {
    @Test
    public void LineInputReadTest() throws IOException {
        LineInputStream lineInputStream = new LineInputStream("D:\\work\\ljx\\tran\\src\\test\\resources\\LineInputStream.txt", "<>", "UTF-8");
        String v;
        while ((v = lineInputStream.raedLine()) != null) {
            System.out.println(v);
        }
    }

    @Test
    public void ByteCharTest() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        byteBuffer.putInt(1);
    }
}
