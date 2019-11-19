package com.wyj.test.netty.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/18
 */
public class Client2 {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 1234));

        String words = "i am client";
        NioUtils.write(socketChannel, words.getBytes());
        System.out.println("Client sending: " + words);
        byte[] readBytes = NioUtils.read(socketChannel);
        if (readBytes != null) {
            System.out.println("Client received: " + new String(readBytes));
        }
        words = "Bye";
        NioUtils.write(socketChannel, words.getBytes());
        readBytes = NioUtils.read(socketChannel);
        if (readBytes != null) {
            System.out.println("Client received: " + new String(readBytes));
        }
//        socketChannel.close();
    }
}
