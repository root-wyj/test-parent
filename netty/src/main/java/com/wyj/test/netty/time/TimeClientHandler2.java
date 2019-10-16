package com.wyj.test.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 由于 数据包传送的是一堆字节，而不是 一条一条的消息，所以 我们有必要做一定的处理保证 我们的消息体全部到了，再去读取
 * 这里创建了 内部积累缓冲区，等待所有的4个字节都被接收到缓冲区了 再去读取
 * Created
 * Author: wyj
 * Date: 2019/10/16
 */
public class TimeClientHandler2 extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf m = (ByteBuf)msg;

        buf.writeBytes(m);
        m.release();

        if (buf.readableBytes() >= 4) {
            long currTime = (buf.readUnsignedInt() - 2208988800L) * 1000;
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currTime)));
            ctx.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
