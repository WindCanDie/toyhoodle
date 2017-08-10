package com.toy.util;

/**
 * Created by Administrator on
 * 2017/8/10.
 */
public class ClassUtil {
    public static Object getObject(String clzz) {
        Class clazz;
        Object obj;
        try {
            clazz = Class.forName(clzz);
            assert clazz != null;
            obj = clazz.newInstance();
        } catch (ClassNotFoundException |IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("ClassNotFoundException");
        }
        return obj;
    }
}
