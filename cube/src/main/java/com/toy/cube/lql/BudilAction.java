package com.toy.cube.lql;

import java.sql.Connection;
import java.util.List;

/**
 * Created by ljx on
 * 2017/8/5.
 */
public class BudilAction {
    public static Action.IF IF(String condition, List<Action> _true_, List<Action> _else_) {
        return new Action.IF(condition, _true_, _else_);
    }

    public static Action FOR(String condition, List<Action> action) {
        return new Action.WHILE(condition, action);
    }

    public static Action QUERY(String sql, Connection conn, Param name) {
        return new Action.QUERY(sql, name, conn);
    }

    public static Action ASSIGNMENT(Param param, String value) {
        return new Action.ASSIGNMENT(param, value);
    }

    public static Action FOREACH(Param table, List<Action> action, Param... param) {
        assert table.dataType == Param.DataType.Table;
        return new Action.FOREACH(table, param, action);
    }
}
