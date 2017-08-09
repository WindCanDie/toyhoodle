package com.toy.cub;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ljx on
 * 2017/8/4.
 */
public class StringTest {
    @Test
    public void trimTest() {
        String test = "aa";

        String[] a = test.split("[.]");

        System.out.print(test.trim());
    }

    @Test
    public void mapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("aadf", "a");
        map.put("bdf", "b");
        map.put("cdfadsfads", "c");
        map.put("cdfa", "c");
        map.put("dfd", "d");
        for (Map.Entry<String, Object> m : map.entrySet()) {
            System.out.println(m.getKey());
        }

    }

    @Test
    public void compareTOTest() {

    }

    @Test
    public void indexTest() {
        String sql = "select * from table1 where a=@abc and base = @anc";

        int startIndex;
        String chage = sql;
        while ((startIndex = chage.indexOf("@")) != -1) {
            int endIndex = chage.indexOf(" ", startIndex);
            String param;
            if (endIndex == -1)
                param = chage.substring(startIndex);
            else
                param = chage.substring(startIndex, endIndex);
            chage = chage.replace(param, "1");
            System.out.println(chage);
        }
    }
}
