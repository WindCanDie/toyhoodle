package com.toy.jdbc.test;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ljx on
 * 2017/8/3.
 */
public class MysqlConnect {
    private static String url = "jdbc:mysql://127.0.0.1:10000/test";

    @Test
    public void jdbcTest() throws ClassNotFoundException, SQLException {
        Connection conn;

        conn = DriverManager.getConnection(url, "root", "921028");

        ResultSet rs = conn.prepareStatement("SELECT * from test").executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
        }
    }

    @Test
    public void socketTest() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8088);
        System.out.print("aaa");
        socket.close();
    }
}
