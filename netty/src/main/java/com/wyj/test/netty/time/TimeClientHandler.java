package com.wyj.test.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 最简单的时间byte 数据读取处理，直接读
 * Created
 * Author: wyj
 * Date: 2019/10/16
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf m = (ByteBuf)msg;

        try {
            long currTime = (m.readUnsignedInt() - 2208988800L) * 1000;
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currTime)));
            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
