package com.toy.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on
 * 2017/6/1.
 */
public class ThreadUtil {
    public static ThreadFactory namedThreadFactory(String prefix) {
        return new ThreadFactoryBuilder().setDaemon(true).setNameFormat(prefix + "-%d").build();
    }

    public static ThreadPoolExecutor newDaemonFixedThreadPool(int nThreads, String prefix) {
        ThreadFactory threadFactory = namedThreadFactory(prefix);
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads, threadFactory);
    }
}
