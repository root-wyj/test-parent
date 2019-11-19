package com.wyj.test.netty.reactor;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/18
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        int opts = SelectionKey.OP_CONNECT | SelectionKey.OP_READ;
        Selector selector = Selector.open();
        socketChannel.register(selector, opts);

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 1234));

        while (true) {
            if (selector.select() <= 0) {
                System.out.println("select unfind");
                Thread.sleep(1000);
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isConnectable()) {
                    System.out.println("connection");
//                    String writeStr = randomStr();
//                    System.out.println("write:"+writeStr);
//                    NioUtils.write(socketChannel, writeStr.getBytes());
//
//                    Thread.sleep(1000);

                } else if (next.isReadable()){
                    System.out.println("readable");
                    byte[] read = NioUtils.read(socketChannel);
                    System.out.println("read:"+new String(read));
                    String writeStr = randomStr();
                    System.out.println("write:"+writeStr);
                    NioUtils.write(socketChannel, writeStr.getBytes());

                    Thread.sleep(1000);
                } else if (next.isWritable()) {
                    System.out.println("writable");
                    NioUtils.write(socketChannel, randomStr().getBytes());

                    Thread.sleep(1000);
                }

                iterator.remove();
            }
        }


    }

    private static String randomStr() {
        String[] arr = {"lalalal", "Bye", "client say!"};
        return arr[new Random().nextInt(3)];
    }
}
