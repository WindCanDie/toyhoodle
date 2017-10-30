package com.toy;

import org.springframework.boot.SpringApplication;

/**
 * Created by Administrator on
 * 2017/8/23.
 */

public class Application {
    public static void main(String[] args) throws Exception {
        WebStart.webStar(args);
    }

    public static void webStar(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}
