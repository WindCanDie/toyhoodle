package com.toy.cache;

import java.util.concurrent.Callable;

public class QueryTask<T> {
    private Callable<T> run;
    private Stust stust = Stust.NotRun;
    private T r;

    public QueryTask(Callable<T> run) {
        this.run = run;
    }

    public T exe() {
        T r = null;
        synchronized (run) {
            if (Stust.NotRun.equals(stust)) {
                try {
                    stust = Stust.Running;
                    r = run.call();
                    this.r = r;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stust = Stust.FINISh;
                }
            } else if (Stust.FINISh.equals(stust)) {
                r = this.r;
            }
        }
        return r;
    }


    enum Stust {
        Running, NotRun, FINISh
    }
}
