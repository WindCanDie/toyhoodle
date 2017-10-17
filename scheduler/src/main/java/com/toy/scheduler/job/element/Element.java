package com.toy.scheduler.job.element;

import java.util.List;

/**
 * Created by Administrator on
 * 2017/9/19.
 */

public abstract class Element {
    protected String id;
    protected List<Element> sub;
    protected List<Element> depend;

    public List<Element> getErrorSub() {
        return errorSub;
    }

    protected List<Element> errorSub;

    public String getId() {
        return id;
    }

    public List<Element> getSub() {
        return sub;
    }

    public List<Element> getDepend() {
        return depend;
    }

    public static final class StarElment extends Element {
    }

    public static final class EndElment extends Element {
    }

    public static final class KillElment extends Element {
    }


}
