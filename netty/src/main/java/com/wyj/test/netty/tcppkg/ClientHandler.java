package com.wyj.test.netty.tcppkg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] req;

    public ClientHandler() {
        req = ("BazingaLyncc is learner" + System.getProperty("line.separator")).getBytes();
//        req = ("In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. His book w"
//                + "ill give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the process "
//                + "of configuring and connecting all of Netty’s components to bring your learned about threading models in ge"
//                + "neral and Netty’s threading model in particular, whose performance and consistency advantages we discuss"
//                + "ed in detail In this chapter you general, we recommend Java Concurrency in Practice by Brian Goetz. Hi"
//                + "s book will give We’ve reached an exciting point—in the next chapter we’ll discuss bootstrapping, the"
//                + " process of configuring and connecting all of Netty’s components to bring your learned about threading "
//                + "models in general and Netty’s threading model in particular, whose performance and consistency advantag"
//                + "es we discussed in detailIn this chapter you general, we recommend Java Concurrency in Practice by Bri"
//                + "an Goetz. His book will give We’ve reached an exciting point—in the next chapter...."
//                + "sdsa ddasd asdsadas dsadasdas" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);

        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);

//        for (int i=0; i<100; i++) {
//            ByteBuf message = Unpooled.buffer(req.length);
//            message.writeBytes(req);
//            ctx.writeAndFlush(message);
//        }

//        ctx.writeAndFlush(req);
//        ctx.writeAndFlush(req);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
