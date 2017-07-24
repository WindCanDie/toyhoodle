package com.toy.tran.db;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by Administrator on
 * 2017/7/20.
 */
public class H2DB {
    private Server service;

    public void start() throws SQLException {
        System.out.println("正在启动h2数据库...");
        service = Server.createTcpServer(new String[]{ "-tcpPort", "9123", "-tcpAllowOthers" }).start();
        System.out.println("h2数据库启动成功...");
    }

    public void stop() {
        if (this.service != null) {
            // 停止H2数据库
            this.service.stop();
            this.service = null;
        }
    }
}
