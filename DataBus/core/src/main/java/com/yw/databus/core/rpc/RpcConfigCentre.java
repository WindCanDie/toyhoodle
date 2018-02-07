package com.yw.databus.core.rpc;

import com.yw.databus.core.Config;

public class RpcConfigCentre {
    /**
     * ms
     *
     * @param config
     * @return
     */
    static int getSocketTimeout(Config config) {
        return config.getIntOrDefault("com.yw.databus.net.socketTimeout", 30000);
    }

    /**
     * ms
     *
     * @param config
     * @return
     */
    static int getConnectTimeout(Config config) {
        return config.getIntOrDefault("com.yw.databus.net.connectTimeout", 10000);
    }
}
