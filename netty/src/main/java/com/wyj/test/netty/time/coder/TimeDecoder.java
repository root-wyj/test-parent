package com.wyj.test.netty.time.coder;

import com.wyj.test.netty.time.bean.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * TimeClientHandler2 的解决方案并不优雅，正常的 我们应该提供 确定的 解码器，由解码器完成 数据的 decode
 *
 * 而且 也不应该返回 ByteBuf 这样的 字节，而应该是 确定的model
 *
 * Created
 * Author: wyj
 * Date: 2019/10/16
 */
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return ;
        }

        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
