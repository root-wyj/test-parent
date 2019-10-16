package com.wyj.test.netty.time.coder;

import com.wyj.test.netty.time.bean.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/16
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter {

    /**
     * 其一，ChannelPromise 是 当编码数据实际写入线路时，Netty 将其标记为 成功或失败
     * 其二，没有调用ctx.flush 。 他有一个单独的处理器方法 void flush(ctx) 用于覆盖 flush 操作
     * 其三，一般用更简单的 MessageToByteEncoder 来作为父类
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime m = (UnixTime)msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt((int)m.value());
        ctx.write(encoded, promise);
    }
}
