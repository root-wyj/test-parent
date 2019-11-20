package com.wyj.test.netty.reactor.reactor2;

import com.wyj.test.netty.reactor.NioUtils;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/19
 */
public class FollowReactor{

    private RoundRobinStrategy strategy;
    private SubWorker[] subWorkers;
    private Selector selector;
    private ExecutorService selectorExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService notifyExecutor = Executors.newSingleThreadExecutor();
    boolean hasRegisted = false;


    public FollowReactor(int workerSize) throws IOException {
        selector = Selector.open();
        strategy = new RoundRobinStrategy(workerSize);
        subWorkers = new SubWorker[workerSize];
        for (int i=0; i<workerSize; i++) {
            subWorkers[i] = new SubWorker(this);
        }
        selectorExecutor.submit(this::start);
    }

    public FollowReactor() throws IOException{
        this(Runtime.getRuntime().availableProcessors()*2);
    }

    public void register(SocketChannel channel) {
        try {

//            notifyExecutor.submit(() -> {
//                try {
//                    Thread.sleep(500);
//                    NioUtils.print("wakeup1");
//                    selector.wakeup();
////                    NioUtils.print("wakeup2");
////                    selector.wakeup();
//                    NioUtils.print("wakeup after");
////                    int i = selector.selectNow();
////                    NioUtils.print("selectNow:"+i);
////                    if (i >0 ) processSelectKeys();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
////                selector.wakeup();
////                NioUtils.print("wake up");
//            });
            NioUtils.print("register be ");
            hasRegisted = true;
            selector.wakeup();
            NioUtils.print("wakeup1");
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_CONNECT);
            NioUtils.print("register ed ");
//            selector.wakeup();
//            NioUtils.print("wakeup2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    1. 先创建selector，然后启动线程 调用selector.select 方法. select默认会阻塞直到 有感兴趣的事件到来
    2. 主reactor 监听 accept 事件，监听到了之后 将socketChannel 注册到 从reactor上，也就是 上面的selector上。
    3. 这时候 会发现，socketChannel.register(selector, ints) 会阻塞，register 之后的代码 根本不会执行
       这时候 会发现，client 发消息，selector 并不能成功监听到 read 事件
       而且尝试在register 之前 启动线程 延迟调用 selector.wakeup 也不行。。。
    4. 最后，只好使用 select(timeout) 的方式 来跑了。。。。
       暂时没有找到更好的方式。。。

    最后找到了，register方法的注释上也写着。 register 会同步修改 interest，并且
     */
    private void start() {
        try {
//                while (selector.select() > 0) {
            while (true) {
                int i = selector.select();
                // 也可以 理解为 wakeup 之后 select 太快了，导致 register 还是阻塞的，关键是 register 为什么会阻塞住呢
                // 整个流程 如果这句话 注释掉 就走不通了，怀疑是 std io 影响到了
//                NioUtils.print("select:"+i);
                if (hasRegisted) {
                    try {
                        Thread.sleep(100);
                        hasRegisted = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (i <= 0) continue;

//                if (selector.select(500) <= 0) {
//                    continue;
//                }

                processSelectKeys();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSelectKeys() throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey next = iterator.next();
            iterator.remove();

            if (next.isReadable()) {
                SocketChannel channel = (SocketChannel) next.channel();
                byte[] read = NioUtils.read(channel);

                if (read == null) {
                    NioUtils.print("socked disconnect:"+channel);
                    channel.close();
                } else {
                    submit(channel, read);
                }

            } else if (next.isConnectable()) {
                NioUtils.print("connect "+next.channel());
            }
        }
    }


    public void submit(SocketChannel channel, byte[] data) {
        int index = strategy.nextCandidate();
        subWorkers[index].submit(channel, data);
    }

    private static class SubWorker {

        private final FollowReactor parent;
        private ExecutorService t = Executors.newSingleThreadExecutor();

        SubWorker(FollowReactor parent) {
            this.parent = parent;
        }

        public void submit(final SocketChannel socketChannel, final byte[] data) {
            t.submit(() -> {
                try {
                    String msg = new String(data);
                    NioUtils.print("server receive:" + msg);
                    if ("Bye".equals(msg)) {
                        NioUtils.print("client say bye。 server close the socket");
                        socketChannel.close();
                    } else {

                        NioUtils.write(socketChannel, ("server:" + msg).getBytes());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    // 轮询策略
    private static class RoundRobinStrategy {
        private int index, size;

        RoundRobinStrategy(int size) {
            index = 0;
            this.size = size;
        }


        synchronized int nextCandidate() {
            return index++%size;
        }

    }
}
