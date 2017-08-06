package com.toy.cube.util;

import java.util.ArrayList;

/**
 * Created by ljx on
 * 2017/8/5.
 */
public class StringUtil {
    public static String[] trimSplit(String v, String regex) {
        ArrayList<String> strList = new ArrayList<>();
        for (String s : v.trim().split(regex)) {
            s = s.trim();
            if (s.length() != 0 || !"".equals(s))
                strList.add(s);
        }
        return strList.toArray(new String[0]);
    }
}
