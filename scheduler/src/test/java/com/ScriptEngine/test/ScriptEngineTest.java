package com.ScriptEngine.test;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by Administrator on 2017/9/20.
 */
public class ScriptEngineTest {
    @Test
    public void JsEngine() throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        engine.put("name", "Alex");
        engine.eval("var message = 1==0");
        Object obj = engine.get("message");
        System.out.println(obj);
    }
}
