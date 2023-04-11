package com.neil.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/4 15:49
 * @Version 1.0
 */
public class ChannelWrapper {
    private final ChannelFuture channelFuture;

    public ChannelWrapper(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public boolean isOK() {
        return this.channelFuture.channel() != null && this.channelFuture.channel().isActive();
    }

    public boolean isWritable() {
        return this.channelFuture.channel().isWritable();
    }

    public Channel getChannel() {
        return this.channelFuture.channel();
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }
}
