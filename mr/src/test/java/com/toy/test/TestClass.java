package com.toy.test;

public class TestClass {
    public static void main(String[] agre) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class clazz = classLoader.loadClass("java.lang.String");
        Object object = clazz.newInstance();
        System.out.println(clazz.hashCode());
    }
}






