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
    boolean started = false;


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
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                selector.wakeup();
//                System.out.println("wake up");
//            });
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_CONNECT);
            System.out.println("register ed");
        } catch (ClosedChannelException e) {
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
     */
    private void start() {
        try {
            while (true) {
//                while (selector.select() > 0) {
                if (selector.select(500) <= 0) {
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();

                    if (next.isReadable()) {
                        SocketChannel channel = (SocketChannel) next.channel();
                        byte[] read = NioUtils.read(channel);

                        if (read == null) {
                            System.out.println("socked disconnect:"+channel);
                            channel.close();
                        } else {
                            submit(channel, read);
                        }

                    } else if (next.isConnectable()) {
                        System.out.println("connect "+next.channel());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                    System.out.println("server receive:" + msg);
                    if ("Bye".equals(msg)) {
                        System.out.println("client say bye。 server close the socket");
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
