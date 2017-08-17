package com.toy.snipe.tcp;

import java.net.SocketAddress;
import java.util.List;

/**
 * Created by Administrator on
 * 2017/8/17.
 */
public interface Scheduler {
    String BALANCE = "BALANCE";

    SocketAddress getServerAddress();

    void setServerAddress(List<SocketAddress> address);
}
