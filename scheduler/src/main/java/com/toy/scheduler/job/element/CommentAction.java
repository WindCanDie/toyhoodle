package com.toy.scheduler.job.element;

import com.toy.scheduler.execute.Task;

import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class CommentAction extends Action {
    String cmd;
    String[] arge;


    @Override
    public void init(Properties conf) {

    }

    @Override
    public Task getTask() {
        return null;
    }
}
