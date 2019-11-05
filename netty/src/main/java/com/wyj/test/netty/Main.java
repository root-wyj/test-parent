package com.wyj.test.netty;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.HashMap;
import java.util.Map;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/15
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        testRecycler();

//        testByteBuf();

//        testFastThreadLocal();
    }

    private Recycler.Handle<Main> handler;
    private Main(Recycler.Handle<Main> handler) {
        this.handler = handler;
    }

    public void recycler() {
        handler.recycle(this);
    }
    private static void testRecycler() throws InterruptedException {
        Recycler<Main> mainPool = new Recycler<Main>() {
            @Override
            protected Main newObject(Handle<Main> handle) {
                return new Main(handle);
            }
        };

        final Main[] mainArray = new Main[1];

        Main main1 = mainPool.get();
        System.out.println(main1);
        main1.recycler();
        Main main2 = mainPool.get();
        System.out.println(main2);
//        main2.recycler();
        Thread thread1 = new Thread(() -> mainArray[0] = mainPool.get());
        thread1.start();
        thread1.join();

        mainArray[0].recycler();
    }

    private static void testByteBuf() {
        PooledByteBufAllocator pooledAllocator = PooledByteBufAllocator.DEFAULT;

        // 申请1M
        ByteBuf buffer1 = pooledAllocator.buffer(1024 * 1024);

        // 申请4K
        ByteBuf buffer = pooledAllocator.buffer(4 * 1024);



    }

    private static void testFastThreadLocal() throws InterruptedException {
        final FastThreadLocal<Map<String, Object>> threadLocalMap = new FastThreadLocal<Map<String, Object>>() {
            @Override
            protected Map<String, Object> initialValue() throws Exception {
                return new HashMap<>();
            }

            @Override
            protected void onRemoval(Map<String, Object> value) throws Exception {
                System.out.println("Map value has be removed:"+value);
            }
        };

        final FastThreadLocal<String> threadLocalString = new FastThreadLocal<String>() {
            @Override
            protected void onRemoval(String value) throws Exception {
                System.out.println("String value has be removed:"+value);
            }
        };

        FastThreadLocalThread thread = new FastThreadLocalThread(() -> {
            threadLocalString.set("xxxxx");
            threadLocalMap.get().put("hahah", "xixixi");
            System.out.println(threadLocalMap.get());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        thread.join();
    }

}
