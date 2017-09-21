package com.toy.scheduler.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public abstract class Task {
    private Logger log = LoggerFactory.getLogger(Task.class);
    private String taskId;
    private TaskContext context;
    private Properties properties;

    public String getTaskId() {
        return taskId;
    }

    public Task(Properties conf) {

    }

    public void run() throws IOException {
        boolean isSuccess = false;
        try {
            exec();
            isSuccess = true;
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (isSuccess)
            onSuccess(context);
        else
            onFailed(context);
    }

    protected abstract void exec() throws Exception;

    protected abstract void onSuccess(TaskContext context);

    protected abstract void onFailed(TaskContext context);
}
