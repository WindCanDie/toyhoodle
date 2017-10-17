package com.toy.scheduler.job.element;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class Selector extends Element {
    List<Path> list = null;

    public List<Element> getSub(Map<String, String> param) throws ScriptException {
        List<Element> elements = new ArrayList<>();
        for (Path obj : list) {
            String decide = obj.decide;
            if (handleScript(decide, param))
                elements.addAll(obj.elements);
        }
        return elements;
    }

    private boolean handleScript(String decide, Map<String, String> param) throws ScriptException {
        int star;
        int end;
        while ((star = decide.indexOf("${")) != -1 && (end = decide.indexOf("}")) != -1) {
            String p = param.get(decide.substring(star + 2, end));
            decide.replace(decide.substring(star, end), p);
        }
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        Object v = nashorn.eval(decide);
        if (v instanceof Boolean)
            return (boolean) v;
        else
            throw new RuntimeException("return value isn't boolean ");
    }

    class Path {
        String decide;
        List<Element> elements;

        public Path(String decide, List<Element> elements) {
            this.decide = decide;
            this.elements = elements;
        }
    }

}
