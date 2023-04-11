package com.neil.netty;

import com.neil.handler.NettyDecoder;
import com.neil.handler.NettyEncoder;
import com.neil.handler.NettyServerHandler;
import com.neil.handler.time.TimeDecoder;
import com.neil.handler.time.TimeEncoder;
import com.neil.handler.time.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 14:48
 * @Version 1.0
 */
@Slf4j
@Component
public class NettyRemotingServer {
    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup eventLoopGroupSelector;
    private final EventLoopGroup eventLoopGroupBoss;

    public NettyRemotingServer() {
        // 处理IO的多线程event loop，"BOSS"组用来接收连接，并将连接注册给""worker"
        eventLoopGroupBoss = new NioEventLoopGroup();
        eventLoopGroupSelector = new NioEventLoopGroup();
        // 启动器，负责组装netty组件，启动服务器
        serverBootstrap = new ServerBootstrap();
    }

    public void start(int port) {
        serverBootstrap.group(eventLoopGroupBoss, eventLoopGroupSelector)
                .channel(NioServerSocketChannel.class) // 实例化一个新的Channel给所有的连接
                .option(ChannelOption.SO_BACKLOG, 128)          // 设置socket选项
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 针对NioSocketChannel
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new NettyEncoder(), new NettyDecoder(), new NettyServerHandler());
                    }
                });

        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("Server bound");
                    }
                    else {
                        log.info("Bind attempt failed");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        }
        catch (InterruptedException e1) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e1);
        }
    }

    public void shutdown() {
        log.info("shut down your server.");
        eventLoopGroupSelector.shutdownGracefully();
        eventLoopGroupBoss.shutdownGracefully();
    }
}
