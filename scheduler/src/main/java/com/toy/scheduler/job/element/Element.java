package com.toy.scheduler.job.element;

import java.util.List;

/**
 * Created by Administrator on
 * 2017/9/19.
 */

public abstract class Element {
    protected String id;
    protected List<Element> sub;
    protected List<Element> errorSub;
    protected List<Element> depend;
    protected List<Element> errorDepend;
    protected String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Element> getErrorSub() {
        return errorSub;
    }


    public String getId() {
        return id;
    }

    public List<Element> getSub() {
        return sub;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSub(List<Element> sub) {
        this.sub = sub;
    }

    public void setErrorSub(List<Element> errorSub) {
        this.errorSub = errorSub;
    }

    public void setDepend(List<Element> depend) {
        this.depend = depend;
    }

    public void setErrorDepend(List<Element> errorDepend) {
        this.errorDepend = errorDepend;
    }

    public List<Element> getDepend() {
        return depend;
    }

    public List<Element> getErrorDepend() {
        return errorDepend;
    }

    public static final class StarElment extends Element {
    }

    public static final class EndElment extends Element {
    }

    public static final class KillElment extends Element {
    }
}
