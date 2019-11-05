package com.wyj.test.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/21
 */
public class CustomDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 规定 header 6 byte，2个flag 共2byte，一个int 4byte
     */
    private static final int HEADER_SIZE = 6;

    /**
     *
     * @param maxFrameLength 解码时，处理每个帧数据的最大长度
     * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置
     * @param lengthFieldLength 记录该帧数据长度的 字段本身的长度
     * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负
     * @param initialBytesToStrip 解析的时候需要跳过的字节数
     * @param failFast true 当frame长度超过maxFrameLength 立即报 TooLongFrameException，为false 读取完整个帧再报
     */
    public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null) {
            return null;
        }

        if (in.readableBytes() < HEADER_SIZE) {
            throw new Exception("可读信息比要求的header长度还小");
        }

        // 在读取过程中 readIndex 的指针也在移动
        byte type = in.readByte();
        byte flag = in.readByte();
        int length = in.readInt();

        int readableLength = in.readableBytes();
        if (readableLength < length) {
            throw new Exception("可读信息比内容长度要少。length:" + length + ", readableBytes:" + readableLength);
        }

        byte[] bodyBytes = new byte[length];
        in.readBytes(bodyBytes);
        String body = new String(bodyBytes, StandardCharsets.UTF_8);
        return new CustomMsg(type, flag, length, body);
    }
}
