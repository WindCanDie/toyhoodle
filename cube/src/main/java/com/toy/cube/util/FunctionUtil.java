package com.toy.cube.util;

import com.toy.cube.function.DateAdd;
import com.toy.cube.function.Function;

import java.util.HashMap;
import java.util.Map;

public class FunctionUtil {
    public static Map<String, Class<? extends Function>> getFunctionMap() {
        Map<String, Class<? extends Function>> functionMap = new HashMap<>();
        functionMap.put("DATEADD", DateAdd.class);
        return functionMap;
    }
}
