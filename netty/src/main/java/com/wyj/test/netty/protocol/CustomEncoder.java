package com.wyj.test.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class CustomEncoder extends MessageToByteEncoder<CustomMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomMsg msg, ByteBuf out) throws Exception {
        if (null == msg) {
            throw new Exception("msg is null");
        }

        out.writeByte(msg.getType());
        out.writeByte(msg.getFlag());
        out.writeInt(msg.getLenght());
        out.writeBytes(msg.getBody().getBytes(Charset.forName("utf-8")));
    }
}
