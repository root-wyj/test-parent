package com.wyj.test.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof CustomMsg) {
            System.out.println("receive msg:" + ((CustomMsg) msg).getBody());
        }
    }
}
