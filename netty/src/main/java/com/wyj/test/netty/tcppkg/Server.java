package com.wyj.test.netty.tcppkg;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class Server {

    static final int PORT = 11112;


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 解决TCP 粘包拆包 就是自定义消息的格式，根据实际业务 给定特定的分隔符 可以让服务器 可以区分两条消息。

                            // 关于分隔符 除了 LineBasedFrameDecoder ，还有 DelimeterBaseFrameDecoder, 比如 new DelimiterBasedFrameDecoder(1024,Unpooled.copiedBuffer("$$__".getBytes()))
                            ch.pipeline().addLast(new LineBasedFrameDecoder(2048));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new ServerHandler1());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = server.bind(PORT).sync();


            future.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }


}
