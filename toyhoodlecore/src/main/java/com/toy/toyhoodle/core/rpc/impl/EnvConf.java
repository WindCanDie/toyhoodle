package com.toy.toyhoodle.core.rpc.impl;

import com.toy.toyhoodle.core.conf.ConfigProvider;
import com.toy.toyhoodle.core.conf.HoodlCofig;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ljx on 2017/1/4.
 */
public class EnvConf {
    private ConcurrentHashMap<String,String> config = new ConcurrentHashMap<>();


    public static final String SERVE_PORT = "toy.hoodle.message.serveport";

    public static final String SERVE_HOST = "toy.hoodle.message.servehost";

    public static final String SERVE_NAME = "toy.hoodle.message.servename";

    public EnvConf(String name, int port, String host, HoodlCofig hoodlCofig) {
       // config.putAll(hoodlCofig.);
        config.put(SERVE_HOST, host);
       // config.put(SERVE_PORT, port);
        config.put(SERVE_NAME, name);
    }

    public String get(String key){
        return  config.get(key);
    }
}
