package com.Thread;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadTest {
    @Test
    public void threadTest() throws InterruptedException {
        Thread thread = new Thread(new RunThread());
        thread.start();
        thread.interrupt();
        thread.join();
    }

    @Test
    public void threadTest2() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> task = executor.submit(new RunThread());
        try {
            task.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            task.cancel(true);
        }
        Future<?> task2 = executor.submit(new RunThread());
        try {
            task2.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            task.cancel(true);
        }

    }

    @Test
    public void threadTest3() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> task = executor.submit(new RunThread());
        Thread.sleep(10000);
        task.cancel(true);

    }

    @Test
    public void threadTest4() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> task = executor.submit(new RunThread());
        Thread.sleep(10);
        executor.awaitTermination(1, TimeUnit.SECONDS);
        Thread.sleep(1000);
    }
}
