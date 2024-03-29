package com.wyj.test.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-04-10
 */
public class JavaFuture {
    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();
    static int getMoreData() {
        System.out.println("begin to start compute");
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(1000);
    }
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(JavaFuture::getMoreData);
        Future<Integer> f = future.whenComplete((v, e) -> {
            System.out.println("1" + Thread.currentThread().getName());
            System.out.println(v);
            System.out.println(e);
        });

        CompletableFuture<Integer> f2 =
                CompletableFuture.supplyAsync(JavaFuture::getMoreData).whenCompleteAsync((v, e) -> {
                    System.out.println("3" + Thread.currentThread().getName());
                    System.out.println(v);
                    System.out.println(e);
                });
        System.out.println("f2" + f2.get());
        System.out.println("f" + f.get());

        System.out.println("2" + Thread.currentThread().getName());
        System.in.read();
    }

}
