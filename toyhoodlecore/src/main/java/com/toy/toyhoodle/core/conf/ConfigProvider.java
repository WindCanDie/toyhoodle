package com.toy.toyhoodle.core.conf;

import com.toy.toyhoodle.core.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ljx on 2016/12/25.
 */
interface ConfigProvider {
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

    public class fileConfig implements ConfigProvider {
        public static final String CONFIGFILE = "/conf/hoodle.property";
        public static Map<Object, Object> conf = null;

        public Object getConfig(String key) {
            return conf.get(key);
        }

        public long getConfigToLong(String key) {
            Object f = conf.get(key);
            if (f instanceof String)
                return Long.parseLong((String) f);
            else
                return Long.parseLong(f.toString());
        }

        public int getConfigTOInt(String key) {
            Object f = conf.get(key);
            return Integer.parseInt(f.toString());
        }

        public String getConfigTOString(String key) {
            return conf.get(key).toString();
        }


        public void lodefile() {
            String path = Utils.getClassPath();
            String filepath = path + CONFIGFILE;
            File file = new File(filepath);
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                conf = new HashMap<>(properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
