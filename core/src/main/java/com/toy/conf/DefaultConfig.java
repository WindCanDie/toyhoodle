package com.toy.conf;

import java.util.HashMap;

/**
 * Created by ljx on 2016/12/22.
 */
public class DefaultConfig {
    private static final HashMap<String, String> map = new HashMap<>();

    public static String get(String key) {
        return map.get(key);
    }
}
