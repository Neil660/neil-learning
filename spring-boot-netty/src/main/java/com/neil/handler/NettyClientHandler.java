package com.neil.handler;

import com.neil.protocol.RemotingCommand;
import com.neil.protocol.time.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 15:13
 * @Version 1.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        RemotingCommand cmd = (RemotingCommand) msg;
        System.out.println("客户端接收到的消息：" + new String(cmd.getBody()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
