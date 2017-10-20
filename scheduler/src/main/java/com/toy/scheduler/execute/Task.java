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
public abstract class Task implements Runnable {
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

    public void run() {
        boolean isSuccess = false;
        try {
            exec();
            isSuccess = true;
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (isSuccess) {
            try {
                onSuccess(context);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                onFailed(context);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void exec() throws Exception;

    protected abstract void onSuccess(TaskContext context) throws IOException, InterruptedException;

    protected abstract void onFailed(TaskContext context) throws IOException, InterruptedException;
}
