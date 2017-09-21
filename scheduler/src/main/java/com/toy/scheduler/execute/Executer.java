package com.toy.scheduler.execute;

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

    public void executer(Task fun) throws IOException {
    }

    public void onSuccess(Consumer<Listener> fun) {
        fun.accept(listener);
    }

    public void onFailed(Consumer<Listener> fun) {
        fun.accept(listener);
    }
}

