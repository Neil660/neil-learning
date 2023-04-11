package com.neil.netty.time;

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
 * @Decription https://netty.io/wiki/user-guide-for-4.x.html
 * @Author NEIL
 * @Date 2023/3/19 21:06
 * @Version 1.0
 */
@Slf4j
@Component
public class TimeServer {
    EventLoopGroup eventLoopGroupBoss;
    EventLoopGroup eventLoopGroupSelector;
    ServerBootstrap serverBootstrap;

    public void start(int port) throws Exception {
        // 处理IO的多线程event loop，"BOSS"组用来接收连接，并将连接注册给""worker"
        eventLoopGroupBoss = new NioEventLoopGroup();
        eventLoopGroupSelector = new NioEventLoopGroup();
        try {
            // 启动器，负责组装netty组件，启动服务器
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroupBoss, eventLoopGroupSelector)
                    .channel(NioServerSocketChannel.class) // 实例化一个新的Channel给所有的连接
                    .option(ChannelOption.SO_BACKLOG, 128)          // 设置socket选项
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 针对NioSocketChannel
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TimeEncoder(), new TimeDecoder(), new TimeServerHandler());

                            /*ch.pipeline().addLast(new TimeServerEncoderOutboundHandler()); //将 UnixTime转为bytebuff*/
                            // 添加自定义的handle
                            // 统一编码
                            /*ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8"))); //将bytebuff转为UnixTime*/
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture sync = this.serverBootstrap.bind().sync();
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("Server bound");
                    }
                    else {
                        System.err.println("Bind attempt failed");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void shutdown() {
        log.info("shut down your server.");
        eventLoopGroupSelector.shutdownGracefully();
        eventLoopGroupBoss.shutdownGracefully();
    }
}
