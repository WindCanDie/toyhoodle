package com.toy.conf;

import com.toy.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ljx on
 * <p>
 * 2016/12/25.
 */
public interface ConfigProvider {
    String CONFIGFILE = "hoodle.property";

    public class EvnConfig implements ConfigProvider {
        public String getEnvConfig(String key) {
            return System.getenv(key);
        }
    }

    public class SystemConfig implements ConfigProvider {
        public String getSystemConfig(String key) {
            return System.getProperty(key);
        }
    }


    public class MapConfig implements ConfigProvider {
        private static Map<Object, Object> confFile = null;
        private Map<Object, Object> mergeConf = null;

        static {
            if (confFile == null) lodefile();
        }

        public MapConfig() {
            mergeConf = confFile;
        }

        public MapConfig(Map map) {
            mergeConf = confFile;
            mergeConf.putAll(map);
        }

        public Map<Object, Object> getAllConf() {
            return mergeConf;
        }

        public Object getConfig(String key) {
            return mergeConf.get(key);
        }

        public long getConfigToLong(String key) {
            Object f = mergeConf.get(key);
            if (f instanceof String)
                return Long.parseLong((String) f);
            else
                return Long.parseLong(f.toString());
        }

        public int getConfigTOInt(String key) {
            Object f = mergeConf.get(key);
            return Integer.parseInt(f.toString());
        }

        public double getConfigTODouble(String key) {
            Object f = mergeConf.get(key);
            return Double.parseDouble(f.toString());
        }

        public String getConfigTOString(String key) {
            return mergeConf.get(key).toString();
        }


        private synchronized static void lodefile() {
            String path = Utils.getClassPath();
            String filepath = path + CONFIGFILE;
            File file = new File(filepath);
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                confFile = new HashMap<>(properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
