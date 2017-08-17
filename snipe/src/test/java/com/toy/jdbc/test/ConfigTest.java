package com.toy.jdbc.test;

import com.toy.snipe.conf.Config;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on
 * 2017/8/17.
 */
public class ConfigTest {
    @Test
    public void configTest() throws FileNotFoundException {
        new Config().setConfFile("D:\\work\\ljx\\snipe\\src\\main\\resources\\hoodle.yml");
    }
}
