package com.yw.databus.agent;

import java.util.Map;

public interface Agent {
    enum serviceStatus {
        survival, dead, ossified
    }

    void init(String progressName, Map<String, String> conf);

    String getName();

    String getServicePid();

    boolean restartService();

    boolean collect(AgentCollectSer ser);

    boolean startService();

    boolean stopService();

    boolean askType(String type);

    serviceStatus serviceStatus();
}
