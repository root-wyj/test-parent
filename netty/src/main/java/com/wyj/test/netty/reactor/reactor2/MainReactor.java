package com.wyj.test.netty.reactor.reactor2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/19
 */
public class MainReactor {


    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector mainSelector = Selector.open();
        serverSocketChannel.bind(new InetSocketAddress(1234));
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(mainSelector, SelectionKey.OP_ACCEPT);
        System.out.println("启动服务器。。");

        FollowReactor followReactor = new FollowReactor();

        while (mainSelector.select() > 0) {
            Set<SelectionKey> selectionKeys = mainSelector.selectedKeys();
            System.out.println("收到消息:"+selectionKeys.size());
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                if (next.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("accept:"+socketChannel);
                    followReactor.register(socketChannel);
                } else {
                    throw new RuntimeException("unavailable key:"+next);
                }
            }

        }
    }



}
