package com.toy.scheduler.execute;

import com.toy.scheduler.job.DAGScheduler;
import com.toy.scheduler.job.element.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/22.
 */
public class ActionTask extends Task {
    private Logger log = LoggerFactory.getLogger(ActionTask.class);
    private DAGScheduler scheduler;
    private Action acition;
    private Properties conf;

    public Action getAcition() {
        return acition;
    }

    public ActionTask(Properties conf, Action action, String taskId, DAGScheduler scheduler) {
        super(conf, taskId);
        this.acition = action;
        this.scheduler = scheduler;
    }


    protected void exec() throws Exception {
        log.info("take " + getTaskId() + " start");
        scheduler.taskStar(this);
        acition.init(conf);
        log.info("take " + getTaskId() + " before");
        acition.before();

        log.info("take " + getTaskId() + " action");
        acition.action();

        log.info("take " + getTaskId() + " after");
        acition.after();

        log.info("take " + getTaskId() + " end");
    }

    protected void onSuccess(TaskContext context) throws IOException, InterruptedException {
        acition.onSuccess();
        scheduler.taskSuccess(this);
    }


    protected void onFailed(TaskContext context) throws IOException, InterruptedException {
        acition.onFailed();
        scheduler.taskFailed(this);
    }

}
