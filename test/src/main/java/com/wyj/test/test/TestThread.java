package com.wyj.test.test;

/**
 * @author wuyingjie
 * Date: 2022/3/30
 */
public class TestThread {


    public static void main(String[] args) {
        Thread t1 = new Thread( () -> {
            System.out.println(Thread.currentThread().getName() + "-" + "running");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + "-" + "end");
        });
        t1.start();
        System.out.println(Thread.currentThread().getName() + "-" + "ending");
    }

}
