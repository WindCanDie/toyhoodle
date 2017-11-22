package com.toy.cache.history;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class HashMapTest {
    @Test
    public void TestHashMap() throws InterruptedException {
        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        LinkedBlockingQueue queue = new LinkedBlockingQueue(100000);
        long t1 = System.currentTimeMillis();
        List<Thread> lists = new ArrayList<>();
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Object v = queue.take();
//                    if (v.equals(0))
//                        break;
//                    hashMap.put(v, v);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Thread t = new Thread(() -> {
//                try {
//                    queue.put(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            });
            t.start();
            lists.add(t);
        }
        System.out.println(System.currentTimeMillis() - t2);
        lists.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(System.currentTimeMillis() - t1);
    }
}
