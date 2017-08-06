package com.toy.cube.lql;

import java.sql.Connection;
import java.util.List;

/**
 * Created by ljx
 * on 2017/8/4.
 */
public interface Action {

    String SELECT = "SELECT";
    String ENDQUERY = "ENDQUERY";
    String QUERY = "QUERY";
    String IF = "IF";
    String ELSE = "ELSE";
    String ENDIF = "ENDIF";
    String WHILE = "WHILE";
    String ENDWHILE = "ENDWHILE";
    String FOREACH = "FOREACH";
    String ENDFOREACH = "ENDFOREACH";
    String DATASOURCE = "[DATASOURCE]";
    String PARAM = "[PARAM]";
    String ACTION = "[ACTION]";

    class IF implements Action {
        public String condition;
        public List<Action> _true_;
        public List<Action> _else_;

        public IF(String condition, List<Action> _true_, List<Action> _else_) {
            this.condition = condition;
            this._true_ = _true_;
            this._else_ = _else_;
        }
    }

    class WHILE implements Action {
        public String condition;
        public List<Action> action;

        public WHILE(String condition, List<Action> action) {
            this.condition = condition;
            this.action = action;
        }
    }

    class QUERY implements Action {
        public String sql;
        public Param name;
        public Connection conn;

        public QUERY(String sql, Param name, Connection conn) {
            this.sql = sql;
            this.name = name;
            this.conn = conn;
        }
    }

    class ASSIGNMENT implements Action {
        public Param param;
        public String value;

        public ASSIGNMENT(Param param, String value) {
            this.param = param;
            this.value = value;
        }
    }

    class FOREACH implements Action {
        public Param table;
        public Param[] param;
        public List<Action> action;

        public FOREACH(Param table, Param[] param, List<Action> action) {
            this.table = table;
            this.param = param;
            this.action = action;
        }
    }

}
