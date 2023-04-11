package com.neil.handler.time;

import com.neil.protocol.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/7 23:39
 * @Version 1.0
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
        //out.writeInt((int)msg.value());
        out.writeBytes(msg.getBody());
    }
}
