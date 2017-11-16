package com.Thread;

public class RunThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + Thread.currentThread().isInterrupted());
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(Thread.currentThread().isInterrupted());
        }
    }
}
