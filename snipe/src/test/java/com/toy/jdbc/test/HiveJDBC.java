package com.toy.jdbc.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on
 * 2017/8/9.
 */
public class HiveJDBC {
    @Test
    public  void HiveJdbc() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        String url = "jdbc:hive2://127.0.0.1:8088/hnaudit;auth=noSasl";
        Connection connection =  DriverManager.getConnection(url);
        while (true){

        }
    }

}
