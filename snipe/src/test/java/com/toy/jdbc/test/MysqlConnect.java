package com.toy.jdbc.test;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ljx on
 * 2017/8/3.
 */
public class MysqlConnect {
    private static String url = "jdbc:mysql://127.0.0.1:8088/mydb";

    @Test
    public void jdbcTest() throws ClassNotFoundException, SQLException {
        Connection conn;

        conn = DriverManager.getConnection(url);

        conn.prepareStatement("SELECT * from aa").executeQuery();
        System.out.print("aa");
    }

    @Test
    public void socketTest() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8088);
        System.out.print("aaa");
        socket.close();
    }
}
