package com.toy.toyTest;

import java.util.function.Function;

/**
 * Created by ljx on
 * 2017/1/3.
 */
public class FunctionTest {
    public static void aa(Function<String, String> function) {
        String aa = function.apply("aa");

    }

    public static void main(String[] ages) {
        FunctionTest.aa(a -> a = a + "aa");
    }
}
