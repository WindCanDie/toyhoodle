package com.toy.scheduler.job;

import com.toy.scheduler.execute.Executer;
import com.toy.scheduler.job.element.Action;
import com.toy.scheduler.job.element.DAGSchedulerEvent;
import com.toy.scheduler.job.element.Element;
import com.toy.scheduler.job.element.Selector;

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
    private Properties conf;
    private Executer executer;
    private Map<String, String> param;
    private Element.starElment element;
    private List<Element> finsh;
    private List<Element> filed;
    private List<Element> run;
    private List<Element> success;
    private LinkedBlockingQueue<DAGSchedulerEvent> eventPool;

    public DAGScheduler(Properties conf, Executer executer) {
        this.conf = conf;
        this.executer = executer;
    }

    public void start() throws IOException {
        run();
        analyizeSubElement(element.getSub());
    }

    public void kill() {
    }

    public void jobSubmitted(Job job) throws InterruptedException {
        eventPool.put(new DAGSchedulerEvent.JobSubmitted("JobId", job));
    }

    public void taskSuccess(Action action) throws IOException, InterruptedException {
        finsh.add(action);
        eventPool.put(new DAGSchedulerEvent.TakeSuccess());
    }

    public void taskFailed(Action action) throws IOException, InterruptedException {
        filed.add(action);
        eventPool.put(new DAGSchedulerEvent.TakeFailed());
    }


    private void analyizeSubElement(List<Element> elements) throws IOException {
        for (Element element : elements) {
            if (element instanceof Action) {
                if (dependDetection(element)) ; //TODO: end
            } else if (element instanceof Selector) {
                if (dependDetection(element))
                    analyizeSubElement(element.getSub());
            } else if (element instanceof Element.endElment) {
                //TODO: end
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();
    }

    private void onReceive(DAGSchedulerEvent event) {
        if (event instanceof DAGSchedulerEvent.JobSubmitted) {
            this.handleJobSubmitted((DAGSchedulerEvent.JobSubmitted) event);
        } else if (event instanceof DAGSchedulerEvent.TakeSuccess) {
            this.handleTakeSuccess((DAGSchedulerEvent.TakeSuccess) event);
        } else if (event instanceof DAGSchedulerEvent.TakeFailed) {
            this.handleTakeFailed((DAGSchedulerEvent.TakeFailed) event);
        }
    }

    private void handleJobSubmitted(DAGSchedulerEvent.JobSubmitted job) {

    }

    private void handleTakeSuccess(DAGSchedulerEvent.TakeSuccess job) {

    }

    private void handleTakeFailed(DAGSchedulerEvent.TakeFailed job) {

    }


}
