package com.wyj.test.netty.tcppkg.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.Data;

import java.util.List;

/**
 * @author wuyingjie
 * Date: 2024/7/4
 */
@Data
public class CustomProtocol {


    private int length;
    private byte[] content;

    public static class CustomProtocolEncoder extends MessageToByteEncoder<CustomProtocol> {
        @Override
        protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
            out.writeInt(msg.getLength());
            out.writeBytes(msg.getContent());
        }
    }

    public static class CustomProtocolDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (in.readableBytes() < 4) {
                System.out.println("not enough data int");
                return;
            }
            int length = in.readInt();
            if (in.readableBytes() < length) {
                System.out.println("not enough data content");
                return;
            }

            byte[] content = new byte[length];
            in.readBytes(content);
            CustomProtocol customProtocol = new CustomProtocol();
            customProtocol.setLength(length);
            customProtocol.setContent(content);
            out.add(customProtocol);
        }
    }

}
