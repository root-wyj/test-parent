package com.wyj.test.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author wuyingjie
 * Date: 2022/3/17
 */
public class Main {

    public static void main(String[] args) {
        FutureTask<String> future = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000);
            return "future";
        });
        future.run();
        String s = null;
        try {
            s = future.get(200, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread()
        System.out.println(s);

    }
}
