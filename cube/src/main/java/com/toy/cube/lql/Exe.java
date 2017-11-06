package com.toy.cube.lql;

import com.toy.cube.datasource.JdbcData;
import com.toy.cube.function.Function;
import com.toy.cube.util.FunctionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by ljx on
 * 2017/8/4.
 */
public class Exe {
    private Map<String, Param> paramMap = new HashMap<>();
    private JdbcData jdbcData;
    private Map<String, Connection> connectionMap = new HashMap<>();
    private List<Action> actions = new ArrayList<>();
    private Map<String, Class<? extends Function>> functionMap = new HashMap<>();

    public Param getParam(String name) {
        Param param = paramMap.get(name);
        if (param == null)
            throw new RuntimeException("not definition param [" + name + "]");
        else
            return param;
    }

    public Param[] getParam(String... names) {
        Param[] params = new Param[names.length];
        for (int i = 0; i < names.length; i++) {
            params[i] = getParam(names[i]);
        }
        return params;
    }

    public Exe() {
        jdbcData = new JdbcData();
        functionMap = FunctionUtil.getFunctionMap();
    }


    public Connection getConnection(String name) {
        return connectionMap.get(name);
    }

    public void putParam(String paramName, String type) {
        paramMap.put(paramName.trim(), Param.getParam(type.trim()));
    }

    public void putDataBase(String name, String url, String driveName) throws SQLException, ClassNotFoundException {
        connectionMap.put(name.trim(), jdbcData.getConnection(url.trim(), driveName.trim(), new Properties()));
    }

    public void putAction(Action action) {
        actions.add(action);
    }

    public List<Action> getAction() {
        return actions;
    }
    public Class<? extends Function> getFunction(String name){
        return functionMap.get(name);
    }
}
