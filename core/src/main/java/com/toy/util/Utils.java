package com.toy.util;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ljx on
 * 2016/12/22.
 */
public class Utils {
    public static Map<String, String> getSystemProperties() {
        Set<String> keyset = System.getProperties().stringPropertyNames();
        Map<String, String> systemProperties =
                keyset.stream().collect(Collectors.toMap(key -> key, key -> System.getProperty(key)));
        return systemProperties;
    }

    public static String getClassPath() {
        return Utils.class.getClassLoader().getResource("").getPath();
    }

    public static String getLocalHost() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
