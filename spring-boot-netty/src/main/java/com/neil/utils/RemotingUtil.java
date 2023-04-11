package com.neil.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/4 11:15
 * @Version 1.0
 */
@Slf4j
public class RemotingUtil {
    public static void closeChannel(Channel channel) {
    final String addrRemote = RemotingHelper.parseChannelRemoteAddr(channel);
    channel.close().addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            log.info("closeChannel: close the connection to remote address[{}] result: {}", addrRemote,
                    future.isSuccess());
        }
    });
}
}
