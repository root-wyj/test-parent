package com.wyj.test.netty.tcppkg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class Client {



    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true)
            ;
            ChannelFuture channelFuture = client.connect("192.168.0.60", Server.PORT).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            worker.shutdownGracefully();
        }
    }
}
