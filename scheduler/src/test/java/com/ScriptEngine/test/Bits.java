package com.ScriptEngine.test;

import org.junit.Test;

public class Bits {
    @Test
    public void by() {
        int a = Integer.SIZE - 3;
        int run = -1 << a;
        System.out.print(run);
    }
}
