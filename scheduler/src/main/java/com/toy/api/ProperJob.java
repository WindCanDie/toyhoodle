package com.toy.api;

import com.toy.scheduler.job.Job;
import com.toy.scheduler.job.element.Element;
import com.toy.scheduler.util.CommentUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProperJob {
    private String jobName;
    private String jobId;
    private Map<String, Element> topology;
    private static final String START = "start";
    private Element.StarElment startElement = new Element.StarElment();
    private Element.EndElment endElment = new Element.EndElment();
    private Element.KillElment killElment = new Element.KillElment();

    public ProperJob(String jobName) {
        this(CommentUtil.getJobId(), jobName);
    }

    public ProperJob(String jobName, String jobId) {
        this.jobName = jobName;
        this.jobId = jobId;
        this.topology = new ConcurrentHashMap<>();
    }

    public ProperJob setStar(Element element) {
        Element startElement = topology.putIfAbsent(START, this.startElement);
        if (startElement == null)
            startElement = this.startElement;
        topology.put(element.getName(), element);
        startElement.getSub().add(element);
        element.getDepend().add(startElement);
        return this;
    }

    public ProperJob setFollow(Element element, String followName) {
        if (START.equals(followName)) {
            setStar(element);
            return this;
        }
        Element followElement = topology.get(followName);
        if (followElement == null) {
            throw new RuntimeException("not follow" + followName);
        }
        element.getDepend().add(followElement);
        followElement.getSub().add(element);
        topology.put(element.getName(), element);
        return this;
    }

    public ProperJob setErrFollow(Element element, String elementName, String followName) {
        Element followElement = topology.get(followName);
        if (followElement == null) {
            throw new RuntimeException("not follow" + followName);
        }
        element.getErrorDepend().add(followElement);
        followElement.getErrorSub().add(element);
        return this;
    }


    public Job build() {
        Job job = new Job(jobName, jobId);
        replenishKillAndEnd();
        job.setElement(startElement);
        return job;
    }

    private void replenishKillAndEnd() {
        if (topology.size() == 0) {
            startElement.getSub().add(endElment);
            return;
        }
        for (Map.Entry<String, Element> m : topology.entrySet()) {
            Element e = m.getValue();
            if (!startElement.equals(e)) {
                if (e.getSub().size() == 0) {
                    e.getSub().add(endElment);
                    endElment.getDepend().add(e);
                }
                if (e.getErrorSub().size() == 0) {
                    e.getErrorSub().add(killElment);
                }
            }
        }
    }
}
