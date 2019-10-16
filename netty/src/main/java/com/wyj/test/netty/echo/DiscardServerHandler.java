package com.wyj.test.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/15
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        ChannelFuture channelFuture = ctx.writeAndFlush(in);
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future == channelFuture) {
                ctx.close();
            }
        });

//        System.out.println(in.toString(CharsetUtil.UTF_8));
//        System.out.println("收到消息:");
//        try {
//            while (in.isReadable()) {
//                System.out.print((char)in.readByte());
//                System.out.flush();
//            }
//
//
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
