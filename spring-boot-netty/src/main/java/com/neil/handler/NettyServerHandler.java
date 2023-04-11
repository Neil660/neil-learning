package com.neil.handler;

import com.neil.protocol.RemotingCommand;
import com.neil.protocol.time.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 15:13
 * @Version 1.0
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RemotingCommand cmd = (RemotingCommand) msg;
        log.info("服务端接收到的消息" + new String(cmd.getBody()));
        ctx.writeAndFlush(new RemotingCommand("I'm NettyServer,i haved received your message.Thanks!".getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
