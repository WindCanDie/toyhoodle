package com.toy.snipe;

import com.toy.snipe.conf.Config;
import com.toy.snipe.tcp.NettyServerFactory;

import java.io.FileNotFoundException;

/**
 * Created by ljx on
 * 2017/8/3.
 */
public class Start {
    public static void main(String[] args) throws FileNotFoundException {
        Config config = new Config();
        assert args.length > 0;
        config.setConfFile(args[0]);
        NettyServerFactory factory = new NettyServerFactory(config);
        config.getServer().forEach(factory::createTCPServer);

        while (true) ;
    }
}
