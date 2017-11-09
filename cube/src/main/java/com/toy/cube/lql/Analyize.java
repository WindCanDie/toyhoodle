package com.toy.cube.lql;

import com.toy.cube.datasource.JdbcData;
import com.toy.cube.function.Function;
import com.toy.cube.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by ljx on
 * 2017/8/5.
 */
public class Analyize {
    private Exe exe;

    public Analyize(Exe exe) {
        this.exe = exe;
    }

    public void analyizeExe() throws SQLException {
        List<Action> actions = exe.getAction();
        for (Action action : actions) {
            doAnalyize(action);
        }
    }

    @SuppressWarnings("unchecked ")
    private void doAnalyize(Action action) throws SQLException {
        if (action instanceof Action.IF) {
            Action.IF _if_ = (Action.IF) action;
            List<Action> actions;
            if (_if_Analyize(_if_.condition)) {
                actions = _if_._true_;
            } else {
                actions = _if_._else_;
            }
            for (Action a : actions) {
                doAnalyize(a);
            }
        }
        if (action instanceof Action.WHILE) {
            Action.WHILE _while_ = (Action.WHILE) action;
            while (_if_Analyize(_while_.condition)) {
                List<Action> actions = _while_.action;
                for (Action a : actions) {
                    doAnalyize(a);
                }
            }
        }
        if (action instanceof Action.ASSIGNMENT) {
            Action.ASSIGNMENT assignment = (Action.ASSIGNMENT) action;
            assignment.param.value = assignmentAnalyize(assignment.value);
        }
        if (action instanceof Action.QUERY) {
            Action.QUERY query = (Action.QUERY) action;
            List<Map<String, Object>> r = JdbcData.query(query.conn, paramChage(query.sql));
            if (query.name != null)
                query.name.value = r;
        }
        if (action instanceof Action.FOREACH) {
            Action.FOREACH foreach = (Action.FOREACH) action;
            List<Map<String, Object>> tables = (List<Map<String, Object>>) foreach.table.value;
            for (Map<String, Object> map : tables) {
                int i = 0;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (i > foreach.param.length)
                        break;
                    foreach.param[i].value = entry.getValue();
                    i++;
                }
                List<Action> actions = foreach.action;
                for (Action a : actions) {
                    doAnalyize(a);
                }

            }
        }
    }

    private String paramChage(final String v) {
        int startIndex;
        String chage = v;
        while ((startIndex = chage.indexOf("@")) != -1) {
            int endIndex = getEndIndex(chage, startIndex);
            String paramName;
            if (endIndex == -1)
                paramName = chage.substring(startIndex);
            else
                paramName = chage.substring(startIndex, endIndex);
            Param param = exe.getParam(paramName);

            if (param.dataType == Param.DataType.String)
                chage = chage.replace(paramName, "'" + param.value + "'");
            else
                chage = chage.replace(paramName, param.value.toString());
        }

        return chage;
    }

    private int getEndIndex(String sql, int startIndex) {
        int endIndex1 = sql.indexOf(" ", startIndex);
        int endIndex2 = sql.indexOf(",", startIndex);
        int endIndex3 = sql.indexOf(")", startIndex);
        Set<Integer> set = new TreeSet<>();
        set.add(endIndex1);
        set.add(endIndex2);
        set.add(endIndex3);
        set.remove(-1);
        Iterator<Integer> it = set.iterator();
        if (it.hasNext())
            return it.next();
        else
            return -1;
    }


    private boolean _if_Analyize(String condition) {
        String[] split = StringUtil.trimSplit(condition, " ");
        assert split.length == 3;
        if ("==".equals(split[1])) {
            return getParamValue(split[0]).toString().equals(getParamValue(split[2]));
        } else if ("<".equals(split[1])) {
            throw new RuntimeException("If can not " + split[2]);

        } else if (">".equals(split[1])) {
            return Integer.parseInt(getParamValue(split[0]).toString()) > Integer.parseInt(getParamValue(split[2]).toString());
        } else if (">=".equals(split[1])) {
            throw new RuntimeException("If can not " + split[2]);

        } else if ("<=".equals(split[1])) {
            throw new RuntimeException("If can not " + split[2]);

        } else if ("!=".equals(split[1])) {
            return !getParamValue(split[0]).equals(getParamValue(split[2]));
        }
        throw new RuntimeException("If can not " + split[2]);
    }

    private Object assignmentAnalyize(String v) {
        if (v.contains("(") && v.endsWith(")")) {
            String[] spileFunction = v.split("[(]");

            String functionName = spileFunction[0];
            String[] param = paramChage(spileFunction[1].substring(0, spileFunction[1].length() - 1)).split(",");

            Class[] paramtype = new Class[param.length];

            for (int i = 0; i < param.length; i++) {
                paramtype[i] = getDataType(param[i]);
            }
            Class<? extends Function> functionClazz = exe.getFunction(functionName);
            Object result;
            try {
                Method exec = functionClazz.getDeclaredMethod("exec", paramtype);
                result = exec.invoke(functionClazz.newInstance(), (Object[]) param);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("function is not exeit");
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException("function  runned failed");
            }
            return result;
        } else {
            return getParamValue(v);
        }
    }

    private Class getDataType(String v) {
        if (v.startsWith("'") && v.endsWith("'")) {
            return String.class;
        } else if (v.contains(".")) {
            return Double.class;
        } else {
            return Integer.class;
        }
    }

    @SuppressWarnings("unchecked ")
    private Object getParamValue(String param) {
        if (!param.startsWith("@") && !param.contains("."))
            return param;
        else {
            String[] ps = param.split("[.]");
            Param p = exe.getParam(ps[0]);
            if (p.dataType == Param.DataType.Table) {
                List<Map<String, Object>> v = (List<Map<String, Object>>) p.value;
                return v.get(0).get(ps[1]);
            }
            return p.value;
        }
    }


}
