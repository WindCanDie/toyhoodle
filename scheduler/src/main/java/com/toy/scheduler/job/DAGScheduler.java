package com.toy.scheduler.job;

import com.toy.scheduler.execute.Executer;
import com.toy.scheduler.job.element.Action;
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
    private LinkedBlockingQueue<Action> wait;

    public DAGScheduler(Properties conf, Executer executer) {
        this.conf = conf;
        this.executer = executer;
        init();
    }

    public boolean start() throws IOException {
        run();
        analyizeSubElement(element.getSub());
        return true;
    }

    private void init() {
        executer.onSuccess(listener -> {
            Action action = listener.getTaskContext().getAction();
            finsh.add(action);
            try {
                analyizeSubElement(action.getSub());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        executer.onFailed(listener -> {
            //TODO ;
        });
    }


    private void analyizeSubElement(List<Element> elements) throws IOException {
        for (Element element : elements) {
            if (element instanceof Action) {
                if (dependDetection(element))
                    wait.add((Action) element);
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

    public void kill() {

    }

    private void run() {
        new Thread(() -> {
            try {
                Action action = wait.take();
                executer.executer(action.getTask());
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).run();
    }
}
