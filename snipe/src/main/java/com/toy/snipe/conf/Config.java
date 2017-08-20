package com.toy.snipe.conf;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;

public class Config {
    private static final String GLOBAL = "global";
    private static final String SERVER = "server";
    private LinkedHashMap grlobalConfig;
    private List<LinkedHashMap> serverConifg;

    public Config() {
    }

    @SuppressWarnings({"unchecked", "SameParameterValue"})
    public void setConfFile(final String dir) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        LinkedHashMap obj = (LinkedHashMap) yaml.load(new FileInputStream(new File(dir)));
        if (obj.get(GLOBAL) != null)
            grlobalConfig = (LinkedHashMap) obj.get(GLOBAL);
        grlobalConfig = new LinkedHashMap();
        serverConifg = (List<LinkedHashMap>) obj.get(SERVER);
    }

    public String getConfig(String key) {
        return (String) grlobalConfig.get(key);
    }

    public int getIntConfig(String key, int def) {
        return grlobalConfig.get(key) == null ? def : (int) grlobalConfig.get(key);
    }

    public List<LinkedHashMap> getServer() {
        return serverConifg;
    }
}
