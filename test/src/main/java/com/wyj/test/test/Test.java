package com.wyj.test.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-03-15
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<Map<String, String>> tl1 = new ThreadLocal<>();
        tl1.set(new HashMap<>());
        ThreadLocal<String> tl2 = new ThreadLocal<>();
        tl2.set(Thread.currentThread().getName() + "tl2");
        Thread t1 = new Thread(() -> {
            tl1.set(new HashMap<>());
            tl2.set(Thread.currentThread().getName() + "tl2");
        });
        t1.start();
        Thread.sleep(10 * 1000);
    }

}
