package com.toy.jdbc.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on
 * 2017/8/9.
 */
public class HiveJDBC {
    @Test
    public void HiveJdbc() throws SQLException, ClassNotFoundException, InterruptedException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        String url = "jdbc:hive2://127.0.0.1:10922/hnaudit";
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                ResultSet resultSet = null;
                try {
                    Connection connection = DriverManager.getConnection(url);
                    resultSet = connection.prepareStatement("select updatetime from app_auditday_today").executeQuery();
                    while (resultSet.next()) {
                        System.out.println(Thread.currentThread().getName() + "-------" + resultSet.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }

}
