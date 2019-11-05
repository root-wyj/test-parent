package com.wyj.test.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    int hbCount=0;

    private static final int MAX_HB_COUNT = 5;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state() == IdleState.WRITER_IDLE && ++hbCount < MAX_HB_COUNT) {
                System.out.println("WRITER_IDLE, 发送心跳");
                ctx.writeAndFlush("client heartbeat");
            } else {
                System.out.println("WRITER_IDLE, 客户端不想发了");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client started and active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client stopped and inactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
