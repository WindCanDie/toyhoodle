package com.toy.tarn.test;

import com.toy.tran.data.db.H2DB;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Administrator on
 * 2017/7/20.
 */
public class H2Test {
    @Test
    public void startH2Test() throws SQLException {
        new H2DB().start();
    }

    @Test
    public void test() {
//        String a = "\\n";
//        String b = "a";
        String c = "阿爱的发生的发生的";
//        String d = " ";
//        byte[] v = a.getBytes();
//        byte[] y = b.getBytes();
        byte[] z = c.getBytes();
//        byte[] x = d.getBytes();
        new String(z);
        System.out.print("");
    }

    public static void main(String[] arges) throws SQLException {
        new H2DB().start();
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(100000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
