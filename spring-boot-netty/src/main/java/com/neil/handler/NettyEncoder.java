package com.neil.handler;

import com.neil.protocol.RemotingCommand;
import com.neil.protocol.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 15:11
 * @Version 1.0
 */
public class NettyEncoder extends MessageToByteEncoder<RemotingCommand> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RemotingCommand msg, ByteBuf out) {
        out.writeBytes(msg.getBody());
    }
}
