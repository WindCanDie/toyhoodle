package com.toy.snipe;

import com.toy.snipe.tcp.NettyServerFactory;

/**
 * Created by ljx on
 * 2017/8/3.
 */
public class Start {
    public static void main(String[] args) {
        NettyServerFactory factory = new NettyServerFactory();
        factory.createTCPServer("127.0.0.1", 10000);
        while (true) ;
    }
}
