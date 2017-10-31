package com.toy;

import com.toy.h2.H2DB;
import org.springframework.boot.SpringApplication;

/**
 * Created by Administrator on
 * 2017/8/23.
 */

public class Application {
    public static void main(String[] args) throws Exception {
        H2DB db = new H2DB();
        db.start();

        WebStart.webStar(args);
    }
    


}
