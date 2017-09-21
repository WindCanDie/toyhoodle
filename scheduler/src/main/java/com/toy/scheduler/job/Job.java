package com.toy.scheduler.job;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class Job {
    private DAGScheduler DAGTask;
    private String jobId;
    private String name;
    private Properties Jobconf;

    public void start() throws IOException {
        DAGTask.start();
    }
}
