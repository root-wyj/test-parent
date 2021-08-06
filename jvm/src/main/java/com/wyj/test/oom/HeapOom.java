package com.wyj.test.oom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:NewRatio=4
 * -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70
 * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./java_pid.hprof
 * -XX:+PrintTenuringDistribution 打印年龄分布
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-07-26
 */
public class HeapOom {

    public static void main(String[] args) {
        oom2();
    }

    /**
     * 最大最小内存都是 20M
     * -Xms 20m -Xmx 20m
     */
    private static void oom1() {
        byte[] arr = new byte[1024 * 1024 * 20];
    }

    /**
     *
     */
    private static void oom2() {
        List<Object> list = new ArrayList<>();
        long i=0;
        while (true) {
            list.add(new Object());
            i++;
            if (i % 1000 == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
