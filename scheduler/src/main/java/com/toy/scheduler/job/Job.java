
package com.toy.scheduler.job;

import com.toy.scheduler.job.element.Element;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class Job extends Element {
    private Properties Jobconf;
    private Element.StarElment element;

    public Job() {

    }

    public Job(String name, String id) {
        super.name = name;
        super.id = id;
    }

    public void stimit() throws IOException {
    }

    public Element.StarElment getElement() {
        return element;
    }

    public void setElement(StarElment element) {
        this.element = element;
    }
}
