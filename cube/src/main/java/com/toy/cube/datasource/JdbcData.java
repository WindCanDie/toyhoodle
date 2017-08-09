package com.toy.cube.datasource;

import com.toy.cube.Config;

import java.sql.*;
import java.util.*;

/**
 * Created by ljx on
 * 2017/8/4.
 */
public class JdbcData {
    Config config;

    public JdbcData() {

    }

    public JdbcData(Config config) throws ClassNotFoundException {
        this.config = config;
        init();
    }

    public void init() throws ClassNotFoundException {
        List<String> dirveList = null;
        if (config != null)
            dirveList = config.getDriveList();
        assert dirveList != null;
        for (String dirve : dirveList)
            Class.forName(dirve);
    }

    public Connection getConnection(String url, String driveClass, Properties properties) throws ClassNotFoundException, SQLException {
        Class.forName(driveClass);
        return getConnection(url, properties);
    }


    public Connection getConnection(String url, Properties properties) throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static List<Map<String, Object>> query(Connection conn, String sql) throws SQLException {
        System.out.println(sql);
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet == null)
            return null;
        else
            return analyizeResultSet(resultSet);
    }

    public static List<Map<String, Object>> analyizeResultSet(ResultSet resultSet) throws SQLException {
        int cloumCont = resultSet.getMetaData().getColumnCount();
        List<Map<String, Object>> result = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 0; i < cloumCont; i++) {
                row.put(resultSet.getMetaData().getColumnLabel(i + 1), resultSet.getObject(i + 1));
            }
            result.add(row);
        }
        return result;
    }
}
