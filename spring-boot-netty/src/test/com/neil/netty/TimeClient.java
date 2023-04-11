package com.neil.netty;

import com.neil.handler.time.TimeClientHandler;
import com.neil.handler.time.TimeDecoder;
import com.neil.handler.time.TimeEncoder;
import com.neil.protocol.time.UnixTime;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Decription 客户端跟服务端唯二的区别就是Bootstrap 和 Channel
 * @Author NEIL
 * @Date 2023/3/20 17:42
 * @Version 1.0
 */
@Slf4j
public class TimeClient {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            args = new String[2];
            args[0] = "127.0.0.1";
            args[1] = "8080";
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // 非服务器Channel的启动器
            b.group(workerGroup); // 只有一个组，那这个组充当"BOSS"跟"worker"
            b.channel(NioSocketChannel.class); // NioSocketChannel用来创建服务端通道
            b.option(ChannelOption.SO_KEEPALIVE, true); // 服务端SockerChannel没有父级，所以没有childOption
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeEncoder(), new TimeDecoder(), new TimeClientHandler());
                }
            });

            // Start the client.客户端用connect，区别于服务端的bind
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("Connection established");
                    }
                    else {
                        System.err.println("Connection attempt failed");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });

            //send msg
            UnixTime msg = new UnixTime("Redis#212312321".getBytes());
            Channel channel = f.channel();
            channel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (f.isSuccess()) {
                        log.info("Sending a msg successful.");
                        return;
                    } else {
                        log.info("Sending a msg failed.");
                    }
                }
            });
        } finally {

        }
    }
}
