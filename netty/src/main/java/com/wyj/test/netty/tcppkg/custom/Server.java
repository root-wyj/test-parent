package com.wyj.test.netty.tcppkg.custom;

import com.wyj.test.netty.SimpleHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 自定义粘包拆包 服务端
 * @author wuyingjie
 * Date: 2024/7/4
 */
public class Server {

    public static void main(String[] args) {
        startNormalServer();
//        startPkgServer();
    }

    /**
     * 普通的server，收到客户端的数据 并不是按照 客户端发送的那样截断的，可能一下就都收过来了
     */
    public static void startNormalServer() {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new SimpleHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        private int cnt;
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                    byte[] bytes = new byte[msg.readableBytes()];
                                    msg.readBytes(bytes);
                                    String message = new String(bytes, StandardCharsets.UTF_8);
                                    System.out.println("ServerHandler read:" + message);
                                    System.out.println("ServerHandler count:" + cnt++);
                                    ByteBuf respBuf = Unpooled.copiedBuffer("第" + cnt + "次:" + UUID.randomUUID().toString(), StandardCharsets.UTF_8);
                                    ctx.writeAndFlush(respBuf);
                                }
                            });
                            ch.pipeline().addLast(new SimpleHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 自定义粘包拆包的server
     */
    public static void startPkgServer() {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        private int cnt;
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new CustomProtocol.CustomProtocolDecoder());
                            ch.pipeline().addLast(new CustomProtocol.CustomProtocolEncoder());
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<CustomProtocol>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
                                    System.out.println("ServerHandler read. size:" + msg.getLength() + ", data:" + new String(msg.getContent(), StandardCharsets.UTF_8));
                                    System.out.println("ServerHandler count:" + cnt++);
                                    byte[] data = ("第" + cnt + "次:" + UUID.randomUUID().toString()).getBytes(StandardCharsets.UTF_8);
                                    CustomProtocol resp = new CustomProtocol();
                                    resp.setLength(data.length);
                                    resp.setContent(data);
                                    ctx.writeAndFlush(resp);
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
