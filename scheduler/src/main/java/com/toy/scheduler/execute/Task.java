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
    private Properties properties;
    private DAGScheduler scheduler;
    private Action acition;

    public String getTaskId() {
        return taskId;
    }

    public Task(Properties conf) {

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
            scheduler.taskSuccess(acition);
        } else {
            onFailed(context);
            scheduler.taskFailed(acition);
        }
    }

    protected abstract void exec() throws Exception;

    protected abstract void onSuccess(TaskContext context);

    protected abstract void onFailed(TaskContext context);
}
