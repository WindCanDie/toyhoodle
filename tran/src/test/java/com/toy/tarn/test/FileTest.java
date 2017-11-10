package com.toy.tarn.test;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileTest {
    @Test
    public void FileWriteTest() throws IOException {
        RandomAccessFile file = new RandomAccessFile("D:\\tmp\\c.txt", "rw");
        file.skipBytes(1);
        FileChannel channel = file.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(100000);
        buf.put("ccbbc000000000000000000000adsfasdfasdfasdfaasdffffffffffffffffffffffffasdfffffasdfqwerqwerqwerqwe00000000000000000000000000000000000000000000000000000000000000000000000000000000asdfasdfffffffffffffasdffffffffffffffffffffffffffffaewrqwerqwerqwezxcvxcvsadfasdfqwefsdfsdf;salkfj;ldskjfoiqwuepoiqwpmfcjsadfkj;adfskjpqwoeiufmxczklcx;jsad;fkweopiuqwpeojkadlmc;zxc;lkdsjf[ew[qweofkadflksadfkjksadkfsa]d[pf[padskf[pqowef[poqwep[wuqre[oiqefjdklajds;fsadfasdlfkjczcxm,c,vzlkcjv;kjdsafqwieo[uweopifrupqweoifjopqwfjmsldknzvmcxknvhoiqujdspfoiqfpewmadkfjas;dflkjw[qeifujzfklajsd;flkjopqiewpomzkvj;lakiouc \n".getBytes());
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            buf.flip();
            channel.write(buf);
        }
        System.out.println(System.currentTimeMillis() - t1);
        channel.force(true);
        channel.close();
    }
}
