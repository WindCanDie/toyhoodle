package com.toy.scheduler.job.element;

import com.toy.scheduler.execute.Task;

import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/20.
 */
public abstract class Action extends Element {
    protected Properties conf;

    public List<Element> errorElement() {
        return null;
    }

    public void setConf(Properties conf) {
        this.conf = conf;
    }

    public abstract void init(Properties conf);

    public abstract Task getTask();

}
