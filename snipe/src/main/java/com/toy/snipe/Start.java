package com.toy.snipe;

import com.toy.snipe.conf.Config;
import com.toy.snipe.tcp.NettyServerFactory;

import java.io.FileNotFoundException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ljx on
 * 2017/8/3.
 */
public class Start extends Thread {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Config config = new Config();
        config.setConfFile(args[0]);
        NettyServerFactory factory = new NettyServerFactory(config);
        config.getServer().forEach(factory::createTCPServer);
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.newCondition().await();

    }
}
