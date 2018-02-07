package com.yw.databus.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {
    public static Object methodInvok(Object instantiation, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args.getClass();
        }
        Method method = instantiation.getClass().getDeclaredMethod(methodName, parameterTypes);
        return method.invoke(instantiation, args);
    }
}
