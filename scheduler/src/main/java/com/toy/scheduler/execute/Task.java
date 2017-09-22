package com.toy.scheduler.execute;

import com.toy.scheduler.job.DAGScheduler;
import com.toy.scheduler.job.element.Action;
import com.toy.scheduler.job.element.Selector;
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
    private Properties conf;

    public String getTaskId() {
        return taskId;
    }

    public Task(Properties conf, String taskId) {
        this.conf = conf;
        this.taskId = taskId;
    }

    public void run() throws IOException, InterruptedException {
        boolean isSuccess = false;
        try {
            exec();
            isSuccess = true;
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (isSuccess) {
            onSuccess(context);
        } else {
            onFailed(context);
        }
    }

    protected abstract void exec() throws Exception;

    protected abstract void onSuccess(TaskContext context) throws IOException, InterruptedException;

    protected abstract void onFailed(TaskContext context) throws IOException, InterruptedException;
}
