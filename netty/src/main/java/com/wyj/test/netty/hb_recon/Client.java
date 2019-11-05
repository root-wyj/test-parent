package com.wyj.test.netty.hb_recon;

import com.wyj.test.netty.heartbeat.Server;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 心跳与重连 https://blog.csdn.net/linuu/article/details/51509847
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class Client {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            client.group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel> () {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                        }
                    });

            client.connect("127.0.0.1", Server.PORT);
        } finally {
            worker.shutdownGracefully();
        }
    }

}
