package com.wyj.test.netty.time;

import com.wyj.test.netty.time.coder.TimeClientHandler3;
import com.wyj.test.netty.time.coder.TimeDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/15
 */
public class TimeClient {

    public static void main(String[] args) throws InterruptedException {
//        String host = args[0];
        String host = "192.168.0.60";
//        int port = Integer.parseInt(args[1]);
        int port = 12000;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
//                            ch.pipeline().addLast(new TimeClientHandler());
//                            ch.pipeline().addLast(new TimeClientHandler2());
                            ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler3());
                        }
                    });

            // start client
            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.addListener((ChannelFutureListener) future -> {
                if (future == f) System.out.println("client started");
            });

            // wait until the connection is closed
            f.channel().closeFuture().sync();
            System.out.println("client closed");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
