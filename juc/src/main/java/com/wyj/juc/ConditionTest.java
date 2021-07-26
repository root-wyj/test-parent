package com.wyj.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-06-14
 */
public class ConditionTest {

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] array = new Object[100];
    int putIndex, takeIndex, count;

    private void put(Object o) {
        lock.lock();

        try {
            while (count == array.length) {
                notFull.await();
            }
            array[putIndex++] = o;
            if (putIndex == array.length) {
                putIndex = 0;
            }
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private Object take() {
        lock.lock();
        Object ret = null;
        try {
            while (count == 0) {
                notEmpty.await();
            }
            ret = array[takeIndex++];
            if (takeIndex == array.length) {
                takeIndex = 0;
            }
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
           lock.unlock();
        }
        return ret;
    }


}
