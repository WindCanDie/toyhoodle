package com.toy.toyhoodle.core.conf;

/**
 * Created by ljx on 2016/12/22.
 *
 */
public class ConfigReader {
    public static String getSystemConfig(String key) {
        return System.getProperty(key);
    }

    public static String getEnvConfig(String key) {
        return System.getenv(key);
    }

    public static String getFieldConfig(String key) {
        return DefaultConfig.get(key);
    }
}
