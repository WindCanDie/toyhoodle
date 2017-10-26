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

    public ActionTest(String name) {
        super(name);
    }

    public ActionTest(String id, String name) {
        super(id, name);
    }


    @Override
    public void init(Properties conf) {
        log.info(name + "init conf " + conf);
    }

    @Override
    public void before() {
        log.info(name + "before");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void action() {
        log.info(name + "action");
    }

    @Override
    public void after() {
        log.info(name + "after");
    }

    @Override
    public void onSuccess() {
        log.info(name + "onSuccess");
    }

    @Override
    public void onFailed() {
        log.info(name + "onFailed");
    }

    @Override
    public Map<String, String> getReturn() {
        return new HashMap<>();
    }
}
