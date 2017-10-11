package com.toy.scheduler.execute;

import com.toy.scheduler.job.element.DAGSchedulerEvent;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class Executer {
    private Properties config;
    private Listener listener;

    public void execute(Task task) {
        try {
            task.run();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("execute error " + e);
        }
    }

    public void onSuccess(Consumer<Listener> fun) {
        fun.accept(listener);
    }

    public void onFailed(Consumer<Listener> fun) {
        fun.accept(listener);
    }
}

