package com.toy.cube.lql;

import com.toy.cube.util.StringUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.toy.cube.lql.BudilAction.*;

/**
 * Created by ljx on
 * 2017/8/4.
 */
public class Compile {
    private ReadFile readFile;
    private Model model;
    private Exe exe = new Exe();

    private enum Model {
        datasource, param, action
    }

    public Compile(ReadFile readFile) {
        this.readFile = readFile;
    }

    public Exe compile() throws IOException, SQLException, ClassNotFoundException {
        String lien;
        while ((lien = readFile.readLien()) != null) {
            if (modelTransform(lien))
                continue;
            if (Model.datasource == model) {
                putDataSource(lien);
            } else if (Model.param == model) {
                putParam(lien);
            } else if (Model.action == model) {
                Action action = putAction(lien);
                exe.putAction(action);
            }
        }
        return exe;
    }

    private Action putAction(String lien) throws IOException {
        if (lien == null)
            throw new RuntimeException("exp errer");
        if (lien.startsWith(Action.IF)) {
            String param = lien.substring(2);
            List<Action> _true_ = new ArrayList<>();
            List<Action> _false_ = new ArrayList<>();
            boolean wrench = true;
            String next;
            while ((next = readFile.readLien()) != null) {
                if (next.startsWith(Action.ELSE)) {
                    wrench = false;
                    next = readFile.readLien();
                } else if (next.startsWith(Action.ENDIF))
                    return IF(param.trim(), _true_, _false_);
                if (wrench)
                    _true_.add(putAction(next));
                else
                    _false_.add(putAction(next));
            }
            throw new RuntimeException("not ENDIF");

        } else if (lien.startsWith(Action.QUERY)) {
            lien = lien.substring(5);
            String[] lientReg = StringUtil.trimSplit(lien, "AS");
            Param name = null;
            Connection conn = exe.getConnection(lientReg[0]);
            if (lientReg.length == 2) {
                name = exe.getParam(lientReg[1]);
                if (name == null) {
                    throw new RuntimeException(lientReg[1] + " is not param");
                }
            }
            StringBuilder query = new StringBuilder();
            String next;
            while ((next = readFile.readLien()) != null) {
                if (next.startsWith(Action.ENDQUERY))
                    return QUERY(query.toString(), conn, name);
                query.append(next);
            }
            throw new RuntimeException("not ENDQUERY");
        } else if (lien.startsWith(Action.WHILE)) {
            List<Action> actions = new ArrayList<>();
            String next;
            while ((next = readFile.readLien()) != null) {
                if (next.startsWith(Action.ENDWHILE))
                    return FOR(lien.substring(5).trim(), actions);
                actions.add(putAction(next));
            }
            throw new RuntimeException("not ENDWHILE");

        } else if (lien.startsWith(Action.FOREACH)) {
            List<Action> actions = new ArrayList<>();
            String next;
            String param_s = lien.substring(7).trim();
            String[] vs = StringUtil.trimSplit(param_s, " ");
            Param param = exe.getParam(vs[0]);
            Param[] mapParam = exe.getParam(StringUtil.trimSplit(vs[1], ","));
            while ((next = readFile.readLien()) != null) {
                if (next.startsWith(Action.ENDFOREACH))
                    return FOREACH(param, actions, mapParam);
                actions.add(putAction(next));
            }
            throw new RuntimeException("not ENDFOREACH");
        } else if (lien.startsWith("@")) {
            String[] vs = StringUtil.trimSplit(lien, "=");
            Param param = exe.getParam(vs[0].trim());
            return ASSIGNMENT(param, vs[1].trim());
        } else
            throw new RuntimeException("Compile KeyWord error");
    }

    private boolean modelTransform(String lien) {
        if (lien.startsWith(Action.DATASOURCE)) {
            model = Model.datasource;
            return true;
        } else if (lien.startsWith(Action.PARAM)) {
            model = Model.param;
            return true;
        } else if (lien.startsWith(Action.ACTION)) {
            model = Model.action;
            return true;
        }
        return false;
    }

    private void putParam(String lien) {
        if (!lien.trim().startsWith("@")) {
            throw new RuntimeException("param is startWith @");
        }
        String[] param = StringUtil.trimSplit(lien, "TYPEAS");
        assert param.length == 2;
        exe.putParam(param[0], param[1]);
    }

    private void putDataSource(String lien) throws SQLException, ClassNotFoundException {
        String[] param = StringUtil.trimSplit(lien, " ");
        assert param.length == 3;
        exe.putDataBase(param[0], param[1], param[2]);
    }


}