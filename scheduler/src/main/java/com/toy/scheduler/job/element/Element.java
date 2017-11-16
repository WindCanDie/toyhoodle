package com.toy.scheduler.job.element;

import com.toy.scheduler.util.CommentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on
 * 2017/9/19.
 */

public abstract class Element {
    protected String id;
    protected List<Element> sub;
    protected List<Element> errorSub;
    protected Map<String, List<Element>> depend;
    protected String name;

    public Element(String name) {
        this(CommentUtil.getUUID(), name);
    }

    public Element(String id, String name) {
        this.id = id;
        this.name = name;
        this.sub = new ArrayList<>();
        this.errorSub = new ArrayList<>();
        this.depend = new HashMap<>();
        this.depend.put("success", new ArrayList<>());
        this.depend.put("field", new ArrayList<>());
    }

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
        this.depend.put("success", depend);
    }

    public void setErrorDepend(List<Element> errorDepend) {
        this.depend.put("faile", errorDepend);
    }

    public List<Element> getDepend() {
        return depend.get("success");
    }

    public List<Element> getErrorDepend() {
        return depend.get("faile");
    }

    public static final class StarElment extends Element {
        public static final String START = "start";

        public StarElment() {
            super(START);
        }

        public StarElment(String id, String name) {
            super(id, name);
        }
    }

    public static final class EndElment extends Element {
        public static final String END = "end";

        public EndElment() {
            super(END);
        }

        public EndElment(String id, String name) {
            super(id, name);
        }
    }

    public static final class KillElment extends Element {
        public static final String KILL = "kill";

        public KillElment() {
            super(KILL);
        }

        public KillElment(String id, String name) {
            super(id, name);
        }
    }
}
