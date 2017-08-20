package com.toy.snipe.tcp;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on
 * 2017/8/17.
 */
public class BalanceScheduler implements Scheduler {
    private List<SocketAddress> address;
    private int length;
    private AtomicInteger rotation = new AtomicInteger(1);

    public BalanceScheduler() {
    }

    BalanceScheduler(List<String> address) {
        this.address = new ArrayList<>();
        address.forEach(a ->
                this.address.add(new InetSocketAddress(a.split(":")[0], Integer.parseInt(a.split(":")[1])))
        );
        this.length = address.size();
    }

    @Override
    public SocketAddress getServerAddress() {
        int rotationNum = rotation.getAndUpdate(x -> {
            if (x + 1 > length) return 1;
            else return x + 1;
        });
        return address.get(rotationNum - 1);
    }

    @Override
    public void setServerAddress(List<SocketAddress> address) {
        this.address = address;
        this.length = address.size();
    }
}
