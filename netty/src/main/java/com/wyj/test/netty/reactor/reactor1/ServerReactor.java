package com.wyj.test.netty.reactor.reactor1;

import com.wyj.test.netty.reactor.Client;
import com.wyj.test.netty.reactor.NioUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 单Reactor 多县城的模型
 * 一个reactor 监听多事件 accept,read,write 但是仅仅处理 accept，read和write监听到后 交给线程池处理
 * Created
 * Author: wyj
 * Date: 2019/11/18
 */
public class ServerReactor {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();

        server.socket().bind(new InetSocketAddress(1234));
        server.configureBlocking(false);

        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            if (selector.select() <= 0) {
                System.out.println("select null");
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                if (next.isAcceptable()) {
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("accept socket:"+socketChannel.toString());
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (next.isValid() && next.isReadable()) {
                    // 经过自己的测试，这里的数据 需要同步读取出来，
                    // 如果不读出来，而交给异步任务，那么selector.selectedKeys 认为里面的key 还有，他会一直走这个循环，一直提交任务，一直到selectKey 中channel中的数据被取出来。
                    SocketChannel channel = (SocketChannel) next.channel();
                    byte[] read = NioUtils.read(channel);
                    if (read == null) {
                        System.out.println("client disconnect");
                        next.cancel();
                        channel.close();
                    } else {
                        processors.submit(new ReadProcess(read, channel));
                    }
                }
            }

        }



    }

    static final ExecutorService processors = Executors.newFixedThreadPool(2*Runtime.getRuntime().availableProcessors());
    private static final String CLOSE = "Bye";

    static class ReadProcess implements Callable<Void> {
        SocketChannel socketChannel;
        byte[] data;

        ReadProcess(byte[] data, SocketChannel socketChannel) {
            this.data = data;
            this.socketChannel = socketChannel;
        }


        @Override
        public Void call() throws IOException, InterruptedException {

            String msg = new String(data, StandardCharsets.UTF_8);
            System.out.println("read msg:"+msg);

            if (CLOSE.equals(msg)) {
                if (socketChannel.isOpen()) {
                    socketChannel.close();
                    System.out.println("server close this connection");
                }
            } else {
                String responseMsg = "server receive msg:"+msg;
                NioUtils.write(socketChannel, responseMsg.getBytes());
            }
            return null;
        }
    }


}
