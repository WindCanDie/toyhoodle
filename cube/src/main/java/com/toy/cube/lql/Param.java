package com.toy.cube.lql;

/**
 * Created by ljx on
 * 2017/8/4.
 */
public class Param {
    public enum DataType {
        String, Table, Intgert, Long, Double
    }

    public DataType dataType;
    public Object value;

    public static Param getParam(String type) {
        Param param = new Param();
        type = type.toLowerCase();
        if ("string".equals(type)) {
            param.dataType = Param.DataType.String;
        } else if ("double".equals(type)) {
            param.dataType = Param.DataType.Double;
        } else if ("long".equals(type)) {
            param.dataType = Param.DataType.Long;
        } else if ("int".equals(type)) {
            param.dataType = Param.DataType.Intgert;
        } else if ("table".equals(type)) {
            param.dataType = Param.DataType.Table;
        } else {
            throw new RuntimeException("not hive type");
        }
        return param;
    }
}
