package com.toy.scheduler.execute;

import com.toy.scheduler.job.element.Action;

import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class TaskContext {
    private long startTime;
    private Task task;
    private long endTime;

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getId() {
        return task.getTaskId();
    }

    public Properties getConfig() {
        return null;
    }

    public Action getAction() {
        return null;
    }
}
