package com.neil.handler.time;

import com.neil.protocol.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/7 23:37
 * @Version 1.0
 */
public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
//        if (in.readableBytes() < 4) {
//            return;
//        }
//        out.add(new UnixTime(in.readUnsignedInt()));
        byte[] c = new byte[in.capacity()];
        int i = 0;
        while (in.isReadable()) {
            c[i++] = in.readByte();
        }
        out.add(new UnixTime(c));
    }
}
