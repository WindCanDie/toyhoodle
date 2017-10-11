
package com.toy.scheduler.job;

import com.toy.scheduler.job.element.Element;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class Job {
    private DAGScheduler scheduler;
    private String jobId;
    private String name;
    private Properties Jobconf;
    private Element.starElment element;

    public String getJobId() {
        return jobId;
    }

    public String getName() {
        return name;
    }

    public void stimit() throws IOException {
    }

    public Element.starElment getElement() {
        return element;
    }
}
