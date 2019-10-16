package com.wyj.test.netty.time;

import com.wyj.test.netty.echo.DiscardServerHandler;
import com.wyj.test.netty.time.coder.TimeEncoder2;
import com.wyj.test.netty.time.coder.TimeServerHandler3;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/15
 */
public class TimeServer {
    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
//                            socketChannel.pipeline().addLast(new TimeServerHandler());
                            socketChannel.pipeline().addLast(new TimeEncoder2(), new TimeServerHandler3());
                        }

                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("server starting");
            // bind and start to accept incoming connections
            ChannelFuture f = b.bind(port).sync();
            f.addListener((ChannelFutureListener) future -> System.out.println("server started"));

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
            System.out.println("server stop");
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new TimeServer(12000).run();
    }
}
