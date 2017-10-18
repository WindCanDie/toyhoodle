package com.toy.scheduler.execute;

import com.toy.scheduler.job.element.DAGSchedulerEvent;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class Executer {
    private Properties config;
    private Listener listener;
    private LinkedBlockingQueue queue = new LinkedBlockingQueue();

    public Executer(Properties config) {
        this.config = config;
    }

    public void execute(Task task) {


        try {
            task.run();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("execute error " + e);
        }
    }


    public void close() {

    }
}

