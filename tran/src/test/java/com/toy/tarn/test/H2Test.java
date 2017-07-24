package com.toy.tarn.test;

import com.toy.tran.db.H2DB;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/7/20.
 */
public class H2Test {
    @Test
    public void startH2Test() throws SQLException {
        new H2DB().start();
    }

    public static void main(String[] arges) throws SQLException {
        new H2DB().start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
