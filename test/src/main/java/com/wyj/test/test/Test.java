package com.wyj.test.test;

import sun.misc.SharedSecrets;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-03-15
 */
public class Test {

    public static void main1(String[] args) throws InterruptedException {
        ThreadLocal<Map<String, String>> tl1 = new ThreadLocal<>();
        tl1.set(new HashMap<>());
        ThreadLocal<String> tl2 = new ThreadLocal<>();
        tl2.set(Thread.currentThread().getName() + "tl2");
        Thread t1 = new Thread(() -> {
            tl1.set(new HashMap<>());
            tl2.set(Thread.currentThread().getName() + "tl2");
        });
        t1.start();
        char[] chs = {'a'};
        String s = SharedSecrets.getJavaLangAccess().newStringUnsafe(chs);
        Thread.sleep(10 * 1000);
    }

    static ForkJoinPool pool = new ForkJoinPool(1);
    public static void main(String[] args) {
        Long invoke = pool.invoke(new MyTask(5));
        System.out.println("invoke result:" + invoke);
    }
/*
还是以上面的代码为例，可以看到 想要知道N！的任务拆分成了 (N-1)!,(N-2)!等任务。

而且计算N!是依赖于(N-1)!的结果的，需要等到(N-1)!有了结果，才能继续执行这个任务。而按照之前的理解，任务的阻塞也会阻塞线程，所以需要新启动一个线程来跑新的任务，那就可能会导致，一个分化计算的任务 创建了许多的线程。

如果N=5 那就至少需要4个线程，如果N=100 那就需要99个线程，这明显是不合理的。

所以故意在创建ForkJoinPool，指定线程数是1，并且在执行的过程中 增加了对线程数和队列数的观测。日志如下
![](fork-join-pool-1.jpg)

可以看到，线程数一直都是1，是非常合理的，却不符合我们的预期。

 */
    private static class MyTask extends RecursiveTask<Long> {

        private long a;

        public MyTask(long a) {
            this.a = a;
        }

        @Override
        protected Long compute() {
            System.out.println(Thread.currentThread().getName() + "-start-a:" + a + "-threadCount:"+ pool.getActiveThreadCount() + "-poolSize:" + pool.getPoolSize());
            if (a > 2) {
                ForkJoinTask<Long> subTask = new MyTask(a-1);
                Long invoke = pool.invoke(subTask);
                System.out.println(Thread.currentThread().getName() + "-end-a:" + a + "-threadCount:"+ pool.getActiveThreadCount() + "-poolSize:" + pool.getPoolSize());
                return a * invoke;
            } else {
                System.out.println(Thread.currentThread().getName() + "-end-a:" + a + "-threadCount:"+ pool.getActiveThreadCount() + "-poolSize:" + pool.getPoolSize());
                return a;
            }
        }
    }

}
