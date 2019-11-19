package com.wyj.test.netty.reactor.reactor2;

import java.nio.channels.SocketChannel;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/19
 */
public interface SocketChannelExecutor {

    void submit(SocketChannel socketChannel);
}
