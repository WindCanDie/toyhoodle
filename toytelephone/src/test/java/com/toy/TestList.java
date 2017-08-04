package com.toy;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on
 * 2017/7/26.
 */
public class TestList {
    @Test
    public void AAA() throws InterruptedException {
        LinkedBlockingQueue<String> list = new LinkedBlockingQueue<>();
        list.put("aaa");
    }
}
