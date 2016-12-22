package com.toy.toyhoodle.core.util;


import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ljx on 2016/12/22.
 */
public class Utils {
    public static Map<String, Object> getSystemProperties() {
        Set<String> keyset = System.getProperties().stringPropertyNames();
        Map<String, Object> systemProperties =
                keyset.stream().collect(Collectors.toMap(key -> key, key -> System.getProperty(key)));
        return systemProperties;
    }
}
