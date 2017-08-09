package com.toy.conf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ljx on
 * 2017/1/4.
 */
public class HoodlConfig {
    private ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();

    public HoodlConfig() {
        loadfileProperties();
        loadSystemProperties();
    }

    private void loadfileProperties() {
        for (Map.Entry<Object, Object> objConfig : new ConfigProvider.MapConfig().getAllConf().entrySet()) {
            hashMap.put(String.valueOf(objConfig.getKey()), String.valueOf(objConfig.getValue()));
        }
    }

    private void loadSystemProperties() {
        System.getProperties().stringPropertyNames().forEach(name -> {
            if (name.startsWith("toy.")) {
                hashMap.put(name, System.getProperty(name));
            }
        });
    }

    public String getConfig(String key) {
        return hashMap.get(key);
    }

    public String getConfig(String key, String defaultValue) {
        return hashMap.get(key) == null ? defaultValue : hashMap.get(key);
    }

    @SuppressWarnings("ConstantConditions")
    public long getConfigToLong(String key) {
        Object f = hashMap.get(key);
        if (f instanceof String)
            return Long.parseLong((String) f);
        else
            return Long.parseLong(f.toString());
    }

    public int getConfigTOInt(String key) {
        Object f = hashMap.get(key);
        return Integer.parseInt(f.toString());
    }

    public double getConfigTODouble(String key) {
        Object f = hashMap.get(key);
        return Double.parseDouble(f.toString());
    }

    public Map<String, String> getAllConf() {
        return hashMap;
    }

}
