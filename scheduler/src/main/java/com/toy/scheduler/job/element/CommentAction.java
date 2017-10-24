package com.toy.scheduler.job.element;

import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class CommentAction extends Action {
    String cmd;
    String[] arge;

    public CommentAction(String name) {
        super(name);
    }

    public CommentAction(String id, String name) {
        super(id, name);
    }


    @Override
    public void init(Properties conf) {

    }

    @Override
    public void before() {

    }

    @Override
    public void action() {

    }

    @Override
    public void after() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public Map<java.lang.String, java.lang.String> getReturn() {
        return null;
    }
}


