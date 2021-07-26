package com.wyj.test.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/16
 */
public class SecureChatClient {

    static final String HOST = "127.0.0.1";
    static final int SERVER_PORT = 11111;

    public static void main(String[] args) throws IOException, InterruptedException {
        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SecureChatClientInitializer(sslCtx));

            Channel channel = client.connect(HOST, SERVER_PORT).sync().addListener(future -> System.out.println("建立链接")).channel();

            ChannelFuture lastWriteFuture = null;

            // read commands from stdin
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = br.readLine();
                if (line == null) {
                    continue;
                }

                lastWriteFuture = channel.writeAndFlush(line + "\r\n");

                if ("bye".equals(line)) {
                    channel.closeFuture().sync();
                    break;
                }
            }

            // Wait until all messages are flushed before closing the channel.
            // 意思是要保证你写过去的消息的这个 future 真的完成了，再退出
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }

        } finally {
            group.shutdownGracefully();
        }


    }
}
