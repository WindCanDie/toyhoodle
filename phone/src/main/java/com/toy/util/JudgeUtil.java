package com.toy.util;

/**
 * Created by Administrator on
 * 2017/7/20.
 */
public class JudgeUtil {
    public static void require(Boolean requirement, Object message) {
        if (!requirement)
            throw new IllegalArgumentException("requirement failed: " + message);
    }
}
