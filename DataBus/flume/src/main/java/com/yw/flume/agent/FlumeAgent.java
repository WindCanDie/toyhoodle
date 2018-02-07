package com.yw.flume.agent;


import com.yw.databus.agent.Agent;
import com.yw.databus.agent.AgentCollectSer;

import java.util.Map;

public class FlumeAgent implements Agent {
    public static final String TYPE = "FlumeAgent-1.8";

    @Override
    public void init(String name, Map<String, String> conf) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getServicePid() {
        return null;
    }

    @Override
    public boolean restartService() {
        return false;
    }

    @Override
    public boolean collect(AgentCollectSer ser) {
        return false;
    }

    @Override
    public boolean startService() {
        return false;
    }

    @Override
    public boolean stopService() {
        return false;
    }

    @Override
    public boolean askType(String type) {
        return TYPE.equals(type);
    }

    @Override
    public serviceStatus serviceStatus() {
        return null;
    }
}
