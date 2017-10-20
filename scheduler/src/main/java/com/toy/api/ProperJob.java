package com.toy.api;

import com.toy.scheduler.job.Job;
import com.toy.scheduler.job.element.Element;

import java.util.Map;

public class ProperJob {
    private String jobName;
    private String jobId;
    private Map<String, Element> topology;
    private static final String START = "start";
    private Element.StarElment startElement = new Element.StarElment();
    private Element.EndElment endElment = new Element.EndElment();
    private Element.KillElment killElment = new Element.KillElment();

    public ProperJob(String jobName, String jobId) {
        this.jobName = jobName;
        this.jobId = jobId;
    }

    public ProperJob setStar(Element element, String elementName) {
        Element startElement = topology.putIfAbsent(START, this.startElement);
        topology.put(elementName, element);
        startElement.getSub().add(element);
        return this;
    }

    public ProperJob setFollow(Element element, String elementName, String followName) {
        Element followElement = topology.get(followName);
        if (followElement == null) {
            throw new RuntimeException("not follow" + followName);
        }
        element.getDepend().add(followElement);
        followElement.getSub().add(element);
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
        Job job = new Job();
        replenishKillAndEnd();
        job.setElement(startElement);
        return null;
    }

    public void replenishKillAndEnd() {
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
