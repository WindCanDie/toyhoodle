package com.toy;

import com.toy.h2.H2DB;
import org.junit.Test;

import java.sql.SQLException;

public class TestH2 {
    @Test
    public void startH2() throws SQLException, InterruptedException {
        H2DB h2 = new H2DB();
        h2.start();
    }

    public static void main(String[] arge) throws SQLException {
        H2DB h2 = new H2DB();
        h2.start();
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
