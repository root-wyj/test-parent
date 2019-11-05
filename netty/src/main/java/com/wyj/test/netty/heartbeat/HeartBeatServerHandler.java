package com.wyj.test.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class HeartBeatServerHandler extends SimpleChannelInboundHandler<String> {

    int lossConnectionTimes = 0;

    static final int MAX_LOS_TIMES = 2;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;

            if (event.state() == IdleState.READER_IDLE) {
                lossConnectionTimes++;
                if (lossConnectionTimes >= MAX_LOS_TIMES) {
                    System.out.println("客户端总是不发送信息，认为已断开链接。");
                    ctx.close();
                } else {
                    System.out.println("客户端不发消息，服务器5s 没收到消息了。"+lossConnectionTimes+"次");
                }
            }
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server receive:" + msg);
    }
}
