package com.wyj.test.netty.tcppkg.custom;

import com.wyj.test.netty.SimpleHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyingjie
 * Date: 2024/7/4
 */
public class Client {

    public static void main(String[] args) throws Exception{
        startNormalClient();
//        startPkgClient();
    }

    /**
     * 普通的client，收到服务的返回的消息 可以是一次全部接受完，也可能是分多次 不规则的接受
     * @throws Exception
     */
    public static void startNormalClient() throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                private int cnt = 0;
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    for (int i=0; i<10; i++) {
                                        ByteBuf buffer = Unpooled.copiedBuffer("send from client ", StandardCharsets.UTF_8);
                                        ctx.writeAndFlush(buffer);
                                    }
                                    super.channelActive(ctx);
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                    byte[] bytes = new byte[msg.readableBytes()];
                                    msg.readBytes(bytes);
                                    String message = new String(bytes, StandardCharsets.UTF_8);
                                    System.out.println("client receive data:" + message);
                                    System.out.println("client receive cnt:" + cnt++);
                                }
                            });
                            ch.pipeline().addLast(new SimpleHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void startPkgClient() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new CustomProtocol.CustomProtocolDecoder());
                            ch.pipeline().addLast(new CustomProtocol.CustomProtocolEncoder());
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<CustomProtocol>() {
                                private int cnt;

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    for (int i=0; i<10; i++) {
                                        byte[] bytes = "send from client ".getBytes(StandardCharsets.UTF_8);
                                        CustomProtocol customProtocol = new CustomProtocol();
                                        customProtocol.setLength(bytes.length);
                                        customProtocol.setContent(bytes);
                                        ctx.writeAndFlush(customProtocol);
                                    }
                                    super.channelActive(ctx);
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
                                    System.out.println("client receive data length:" + msg.getLength());
                                    System.out.println("client receive data content:" + new String(msg.getContent(), StandardCharsets.UTF_8));
                                    System.out.println("client receive cnt:" + cnt++);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
