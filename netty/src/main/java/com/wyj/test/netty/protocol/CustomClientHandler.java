package com.wyj.test.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class CustomClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String body = "Hello Netty!!";
        CustomMsg msg = new CustomMsg((byte)0xAB, (byte)0xBC, body.getBytes(StandardCharsets.UTF_8).length, body);
        ctx.writeAndFlush(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
