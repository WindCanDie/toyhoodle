package com.toy.cache.history;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HistoryTest {
    TimeReelHistory history = new TimeReelHistory();
    HistoryTest historyTest;

    @Before
    public void bef() {
        historyTest = new HistoryTest();
    }

    @Test
    public void putAndisCacheTest() throws InterruptedException {

        long time = System.currentTimeMillis();
        List<Thread> lists = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    int num = history.putAndCount(i);
                }
            });
            t.start();
            lists.add(t);
        }
        writ(lists);
        System.out.println(System.currentTimeMillis() - time);
    }

    public void writ(List<Thread> list) {
        list.forEach(r -> {
            try {
                r.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Thread> putKey(String key, int count) throws InterruptedException {
        List<Thread> l = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Thread t = new Thread(() -> {
                int num = history.putAndCount(key);
                // System.out.println("key" + key + ":_______________________" + num);
            });
            t.start();
            l.add(t);
        }
        return l;
    }
}
