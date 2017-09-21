package com.toy.scheduler.job.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on
 * 2017/9/19.
 */
public class Selector extends Element {
    List<Map<String, List<Element>>> list = null;

    public List<Element> getElement(Object... param) {
        List<Element> elements = new ArrayList<>();
        for (Map<String, List<Element>> obj : list) {

        }
        return null;
    }


}
