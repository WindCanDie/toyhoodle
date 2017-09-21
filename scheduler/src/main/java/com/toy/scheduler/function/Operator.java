package com.toy.scheduler.function;

import java.io.IOException;

/**
 * Created by Administrator on
 * 2017/9/20.
 */
@FunctionalInterface
public interface Operator {
    void accept() throws IOException;
}
