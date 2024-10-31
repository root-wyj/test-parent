package com.wyj.test.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * 自定义一个 类似aqs的锁
 */
public class CustomizeLock {

    private static class ThreadNode {
        Thread thread;

        ThreadNode next;

        ThreadNode(Thread t) {
            this.thread = t;
        }
    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Unsafe unsafe = getUnsafe();

    private static final long stateOffset;
    private static final long headOffset;
    private static  final long tailOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(CustomizeLock.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset(CustomizeLock.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset(CustomizeLock.class.getDeclaredField("tail"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    private volatile int state = 0;

    /**
     * 头节点，永远保存的 获取到锁的线程
     */
    private volatile ThreadNode head;

    /**
     * 尾节点
     */
    private volatile ThreadNode tail;

    private final LinkedList<ThreadNode> waitingList = new LinkedList<>();

    public void lock() {
            boolean casResult = unsafe.compareAndSwapInt(this, stateOffset, 0, 1);
            if (!casResult) {
                ThreadNode curNode = new ThreadNode(Thread.currentThread());
                while (true) {
                    ThreadNode t = tail;
                    if (unsafe.compareAndSwapObject(this, tailOffset, t, curNode)) {
                        t.next = curNode;
                        break;
                    }
                }
                unsafe.park(false, 0);
            } else {

                return;
            }
    }

    public void unlock() {
        if (unsafe.compareAndSwapInt(this, stateOffset, 1, 0)) {
            ThreadNode pop = null;
            synchronized (waitingList) {
                if (waitingList.peek() != null) {
                    pop = waitingList.pop();
                }
            }
            if (pop != null) {
                unsafe.unpark(pop.thread);
            }
        }
    }

    private static int count = 0;
    public static void main(String[] args) throws InterruptedException {
        CustomizeLock lock = new CustomizeLock();
        CountDownLatch cdl = new CountDownLatch(10000);
        int sum = 0;
        IntStream.range(0, 10000).forEach(i -> new Thread("tt-" + i) {
            @Override
            public void run() {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " getLock " + count);
                IntStream.range(0, 1000).forEach(i -> {count ++;});
                lock.unlock();
                cdl.countDown();
            }
        }.start());
        cdl.await();
        System.out.println(count);
    }

}
