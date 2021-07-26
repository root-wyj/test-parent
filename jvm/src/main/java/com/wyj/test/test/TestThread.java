package com.wyj.test.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestThread {

    private ReentrantLock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();
    private volatile int flag = 1;

    private void run1() {
        lock.lock();
        try {
            while(flag != 1) {
                cond.await();
            }
            flag = 2;
            System.out.println("run1");
            cond.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void run2() {
        lock.lock();
        try {
            while(flag != 2) {
                cond.await();
            }
            flag = 3;
            System.out.println("run2");
            cond.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void run3() {
        lock.lock();
        try {
            while(flag != 3) {
                cond.await();
            }
            System.out.println("run3");
            cond.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run() {
        new Thread(this::run3).start();
        new Thread(this::run2).start();
        new Thread(this::run1).start();
    }

    public static void main(String[] args) {

        new TestThread().run();
    }
}
