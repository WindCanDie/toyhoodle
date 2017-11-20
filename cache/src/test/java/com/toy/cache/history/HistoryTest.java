package com.toy.cache.history;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HistoryTest {
    TimeReelHistory history = new TimeReelHistory();
    static ExecutorService executorService = Executors.newFixedThreadPool(1000);
    HistoryTest historyTest;

    @Before
    public void bef() {
        historyTest = new HistoryTest();
    }

    @Test
    public void putAndisCacheTest() throws InterruptedException {
        long time = System.currentTimeMillis();
        historyTest.putKey("1", 10000);
//        historyTest.putKey("2", 10000);
//        historyTest.putKey("3", 10000);
//        historyTest.putKey("4", 10000);
        executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        System.out.println(System.currentTimeMillis() - time);
    }

    public void putKey(String key, int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                int num = history.putAndCount(key);
                System.out.println("key" + key + ":_______________________" + num);
            });
        }
    }
}
