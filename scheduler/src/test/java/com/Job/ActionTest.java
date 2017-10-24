package com.Job;

import com.toy.scheduler.execute.ActionTask;
import com.toy.scheduler.job.element.Action;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;

public class ActionTest extends Action {
    private Logger log = LoggerFactory.getLogger(ActionTask.class);

    ActionTest(String name) {
        super(name);
    }

    @Override
    public void init(Properties conf) {
        log.info("init conf " + conf);
    }

    @Override
    public void before() {
        log.info("before");
    }

    @Override
    public void action() {
        log.info("action");
    }

    @Override
    public void after() {
        log.info("after");
    }

    @Override
    public void onSuccess() {
        log.info("onSuccess");
    }

    @Override
    public void onFailed() {
        log.info("onFailed");
    }

    @Override
    public Map<String, String> getReturn() {
        return new HashMap<>();
    }
}
