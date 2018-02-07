package com.yw.databus.core;

import com.yw.databus.util.SystemUtil;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Config extends HashMap<Object, Object> {
    private Properties fileConfig;
    private HashMap<Object, Object> SysConfig;

    public Config() {
        SysConfig = new HashMap<>();
        fileConfig = new Properties();
        loadSysConfig();
    }

    public void loadFileConfig() {
        loadFileConfig("../conf/databus.properties");
    }

    public void loadFileConfig(String path) {
        try {
            fileConfig.load(new FileReader(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.putAll(fileConfig);
    }

    private void loadSysConfig() {
        SysConfig.putAll(SystemUtil.getSystemConf());
    }

    public HashMap<Object, Object> getSysConfig() {
        return SysConfig;
    }

    public int getInt(String key) {
        return Integer.valueOf(String.valueOf(get(key)));
    }

    public int getIntOrDefault(String key, int i) {
        return get(key) == null ? i : Integer.valueOf(String.valueOf(get(key)));
    }

    public String getString(String key) {
        return String.valueOf(get(key));
    }

    public String getStringOrDefault(String key, String i) {
        return get(key) == null ? i : String.valueOf(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.valueOf(String.valueOf(get(key)));
    }

    public boolean getBooleanOrDefault(String key, boolean i) {
        return get(key) == null ? i : Boolean.valueOf(String.valueOf(get(key)));
    }

}
