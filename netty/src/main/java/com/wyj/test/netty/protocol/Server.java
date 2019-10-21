package com.wyj.test.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class Server {
    static final int PORT = 11113;

    private static final int MAX_FRAME_LENGTH = 1024*1024;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_FIELD_OFFSET = 2;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new CustomDecoder(MAX_FRAME_LENGTH, LENGTH_FIELD_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, false));
                            ch.pipeline().addLast(new CustomServerHandler());
                        }
                    });

            ChannelFuture channelFuture = server.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
