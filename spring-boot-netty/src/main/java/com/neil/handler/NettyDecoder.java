package com.neil.handler;

import com.neil.protocol.RemotingCommand;
import com.neil.protocol.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 15:11
 * @Version 1.0
 */
public class NettyDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        byte[] c = new byte[in.capacity()];
        int i = 0;
        while (in.isReadable()) {
            c[i++] = in.readByte();
        }
        out.add(new RemotingCommand(c));
    }
}
