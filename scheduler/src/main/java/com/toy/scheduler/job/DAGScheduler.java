package com.toy.scheduler.job;

import com.toy.scheduler.execute.ActionTask;
import com.toy.scheduler.execute.Executer;
import com.toy.scheduler.job.element.Action;
import com.toy.scheduler.job.element.DAGSchedulerEvent;
import com.toy.scheduler.job.element.Element;
import com.toy.scheduler.job.element.Selector;
import com.toy.scheduler.util.CommentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class DAGScheduler {
    private Logger log = LoggerFactory.getLogger(DAGScheduler.class);
    private Properties conf;
    private Executer executer;
    private Map<String, String> param;
    private LinkedBlockingQueue<Element> finsh;
    private LinkedBlockingQueue<Element> filed;
    private LinkedBlockingQueue<Element> running;
    private LinkedBlockingQueue<Element> success;
    private LinkedBlockingQueue<DAGSchedulerEvent> eventPool;
    private boolean close = false;
    private boolean kill = false;


    public DAGScheduler(Properties conf, Executer executer) {
        this.conf = conf;
        this.executer = executer;
        run();
    }


    public void jobSubmitted(Job job) throws InterruptedException {
        if (close) {
            throw new RuntimeException("Scheduler has close");
        } else {
            eventPool.put(new DAGSchedulerEvent.JobSubmitted(CommentUtil.getJobId(), job));
        }
    }

    public void taskStar(ActionTask task) throws InterruptedException {
        if (close) {
            eventPool.put(new DAGSchedulerEvent.JobEnd());
        } else {
            running.add(task.getAcition());
            eventPool.put(new DAGSchedulerEvent.TakeStart());
        }
    }

    public void taskSuccess(ActionTask task) throws IOException, InterruptedException {
        finsh.add(task.getAcition());
        eventPool.put(new DAGSchedulerEvent.TakeSuccess(task));
    }

    public void taskFailed(ActionTask task) throws IOException, InterruptedException {
        filed.add(task.getAcition());
        eventPool.put(new DAGSchedulerEvent.TakeFailed(task));
    }

    private ActionTask getTake(Action action) {
        String takeId = CommentUtil.getTake();
        return new ActionTask(action.getConf(), action, takeId, this);
    }

    private void analyizeSubElement(List<Element> elements) throws Exception {
        for (Element element : elements) {
            if (element instanceof Action) {
                if (dependDetection(element)) {
                    eventPool.put(new DAGSchedulerEvent.TaskSubmitted(getTake((Action) element)));
                }
            } else if (element instanceof Selector) {
                if (dependDetection(element))
                    analyizeSubElement(((Selector) element).getSub(param));
            } else if (element instanceof Element.EndElment) {
                if (dependDetection(element))
                    eventPool.put(new DAGSchedulerEvent.JobEnd());
            } else if (element instanceof Element.KillElment) {
                kill();
            } else {
                throw new RuntimeException("can not Element");
            }
        }
    }

    private boolean dependDetection(Element element) {
        return finsh.containsAll(element.getDepend());
    }

    private void run() {
        new Thread(() -> {
            try {
                onReceive(eventPool.take());
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }).run();
    }


    private void onReceive(DAGSchedulerEvent event) throws Exception {
        if (event instanceof DAGSchedulerEvent.JobSubmitted) {
            this.handleJobSubmitted((DAGSchedulerEvent.JobSubmitted) event);
        } else if (event instanceof DAGSchedulerEvent.TaskSubmitted) {
            this.handleTaskSubmitited((DAGSchedulerEvent.TaskSubmitted) event);
        } else if (event instanceof DAGSchedulerEvent.TakeSuccess) {
            this.handleTakeSuccess((DAGSchedulerEvent.TakeSuccess) event);
        } else if (event instanceof DAGSchedulerEvent.TakeFailed) {
            this.handleTakeFailed((DAGSchedulerEvent.TakeFailed) event);
        } else if (event instanceof DAGSchedulerEvent.JobEnd) {
            this.handleJobEnd((DAGSchedulerEvent.JobEnd) event);
        }
    }

    private void handleJobEnd(DAGSchedulerEvent.JobEnd jobEnd) {
        log.info("job" + jobEnd.jobid + "success");
        if (kill) {//TODO: JOb Faile can't user filed

        } else {

        }
    }

    private void handleJobSubmitted(DAGSchedulerEvent.JobSubmitted job) throws Exception {
        Element.StarElment starElment = job.job.getElement();
        eventPool.put(new DAGSchedulerEvent.JobStart());
        log.info("Job " + job.job.getName() + "start");
        analyizeSubElement(starElment.getSub());
    }

    private void handleTaskSubmitited(DAGSchedulerEvent.TaskSubmitted task) throws IOException {
        executer.execute(task.task);
    }

    private void handleTakeSuccess(DAGSchedulerEvent.TakeSuccess task) throws Exception {
        success.put(task.task.getAcition());
        log.info("task " + task.task.getTaskId() + "Success");
        Map<String, String> aReturn = task.task.getAcition().getReturn();
        param.putAll(aReturn);
        analyizeSubElement(task.task.getAcition().getSub());
    }

    private void handleTakeFailed(DAGSchedulerEvent.TakeFailed task) throws Exception {
        filed.put(task.task.getAcition());
        log.info("task " + task.task.getTaskId() + "Failed");
        analyizeSubElement(task.task.getAcition().getErrorSub());
    }

    public void kill() {
        kill = true;
        close();
    }

    private void close() {

    }

}
