package com.toy.scheduler.util;

import java.util.UUID;

/**
 * Created by Administrator on
 * 2017/9/22.
 */
public class CommentUtil {
    public static String getJobId() {
        return "RunJobId" + getUUID();
    }

    public static String getTake() {
        return "Take" + getUUID();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
