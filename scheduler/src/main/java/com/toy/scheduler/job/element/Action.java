package com.toy.scheduler.job.element;

import com.toy.scheduler.execute.Task;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/20.
 * init -> before -> action -> after
 */
public abstract class Action extends Element {
    protected Properties conf;

    public Action(String name) {
        super(name);
    }
    public Action(String id, String name) {
        super(id, name);
    }

    public List<Element> errorElement() {
        return null;
    }

    public Properties getConf() {
        return conf;
    }

    public void setConf(Properties conf) {
        this.conf = conf;
    }

    public abstract void init(Properties conf);

    public abstract void before();

    public abstract void action();

    public abstract void after();

    public abstract void onSuccess();

    public abstract void onFailed();

    public abstract Map<String, String> getReturn();
}
