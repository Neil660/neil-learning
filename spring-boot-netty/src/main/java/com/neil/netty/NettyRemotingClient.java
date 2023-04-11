package com.neil.netty;

import com.neil.exception.RemotingConnectException;
import com.neil.handler.NettyClientHandler;
import com.neil.handler.NettyDecoder;
import com.neil.handler.NettyEncoder;
import com.neil.handler.time.TimeClientHandler;
import com.neil.handler.time.TimeDecoder;
import com.neil.handler.time.TimeEncoder;
import com.neil.protocol.RemotingCommand;
import com.neil.utils.ChannelWrapper;
import com.neil.utils.RemotingHelper;
import com.neil.utils.RemotingUtil;
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
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/4/11 14:49
 * @Version 1.0
 */
@Slf4j
public class NettyRemotingClient {
    private static final long LOCK_TIMEOUT_MILLIS = 3000;
    private static final String host = "127.0.0.1";
    private final EventLoopGroup eventLoopGroupWorker;
    ChannelFuture channelFuture;
    private final Bootstrap bootstrap = new Bootstrap();

    /**
     * 存放已经打开的连接
     */
    private final ConcurrentMap<String /* addr */, ChannelWrapper> channelTables = new ConcurrentHashMap<>();
    private final Lock lockChannelTables = new ReentrantLock();

    public NettyRemotingClient() {
        eventLoopGroupWorker = new NioEventLoopGroup();
    }

    /**
     * 开启Netty客户端
     * @param port
     */
    public void start(int port) {
        bootstrap.group(eventLoopGroupWorker) // 只有一个组，那这个组充当"BOSS"跟"worker"
            .channel(NioSocketChannel.class) // NioSocketChannel用来创建服务端通道
            .option(ChannelOption.SO_KEEPALIVE, true) // 服务端SockerChannel没有父级，所以没有childOption
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new NettyEncoder(), new NettyDecoder(), new NettyClientHandler());
                }
            });

        try {
            channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.addListener(new ChannelFutureListener() {
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
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过addr创建连接
     * @param addr
     * @return
     * @throws InterruptedException
     */
    private Channel getAndCreateChannel(String addr) throws InterruptedException {
        if (null == addr) {
            addr = "127.0.0.1:8081";
        }
        ChannelWrapper cw = this.channelTables.get(addr);
        if (cw != null && cw.isOK()) {
            return cw.getChannel();
        }

        return this.createChannel(addr);
    }

    /**
     * 创建channel，channelTables表中有就先从表取
     * @param addr
     * @return
     * @throws InterruptedException
     */
    private Channel createChannel(final String addr) throws InterruptedException {
        ChannelWrapper cw = this.channelTables.get(addr);
        if (cw != null && cw.isOK()) {
            return cw.getChannel();
        }
        if (this.lockChannelTables.tryLock(LOCK_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
            try {
                boolean createNewConnection;
                cw = this.channelTables.get(addr);
                if (cw != null) {
                    if (cw.isOK()) {
                        return cw.getChannel();
                    } else if (!cw.getChannelFuture().isDone()) {
                        createNewConnection = false;
                    } else {
                        this.channelTables.remove(addr);
                        createNewConnection = true;
                    }
                } else {
                    createNewConnection = true;
                }
                // 新的连接需要创建
                if (createNewConnection) {
                    ChannelFuture channelFuture = this.bootstrap.connect(RemotingHelper.string2SocketAddress(addr));
                    log.info("createChannel: begin to connect remote host[{}] asynchronously", addr);
                    cw = new ChannelWrapper(channelFuture);
                    this.channelTables.put(addr, cw);
                }
            } catch (Exception e) {
                log.error("createChannel: create channel exception", e);
            } finally {
                this.lockChannelTables.unlock();
            }
        } else {
            log.warn("createChannel: try to lock channel table, but timeout, {}ms", LOCK_TIMEOUT_MILLIS);
        }

        if (cw != null) {
            ChannelFuture channelFuture = cw.getChannelFuture();
            if (channelFuture.awaitUninterruptibly(3000L)) {
                if (cw.isOK()) {
                    log.info("createChannel: connect remote host[{}] success, {}", addr, channelFuture.toString());
                    return cw.getChannel();
                } else {
                    log.warn("createChannel: connect remote host[" + addr + "] failed, " + channelFuture.toString(), channelFuture.cause());
                }
            } else {
                log.warn("createChannel: connect remote host[{}] timeout {}ms, {}", addr, 3000L, channelFuture.toString());
            }
        }

        return null;
    }

    public void sendMsg(String msg) {
        try {
            invokeSync("127.0.0.1:8081", new RemotingCommand(msg.getBytes()));
        }
        catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        catch (RemotingConnectException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 发送同步消息
     * @param addr
     * @param request
     * @return
     */
    public RemotingCommand invokeSync(String addr, final RemotingCommand request)
            throws InterruptedException, RemotingConnectException {
        final Channel channel = this.getAndCreateChannel(addr);
        if (channel != null && channel.isActive()) {
            try {
                final RemotingCommand response =  new RemotingCommand("".getBytes());
                // 底层发消息的方法
                channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture f) throws Exception {
                        if (f.isSuccess()) {
                            log.info("send a request command to channel <" + addr + "> successful.");
                            response.setBody("successful".getBytes());
                            return;
                        } else {
                            response.setBody("failed".getBytes());
                            log.warn("send a request command to channel <" + addr + "> failed.");
                        }
                    }
                });
                return response;
            } catch (Exception e) {
                log.warn("invokeSync: send request exception, so close the channel[{}]", addr);
                this.closeChannel(addr, channel);
                throw e;
            }
        } else {
            this.closeChannel(addr, channel);
            throw new RemotingConnectException(addr);
        }
    }

    /**
     * 关闭通道channel
     * @param addr
     * @param channel
     */
    public void closeChannel(final String addr, final Channel channel) {
        if (null == channel)
            return;

        final String addrRemote = null == addr ? RemotingHelper.parseChannelRemoteAddr(channel) : addr;

        try {
            if (this.lockChannelTables.tryLock(LOCK_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
                try {
                    boolean removeItemFromTable = true;
                    final ChannelWrapper prevCW = this.channelTables.get(addrRemote);

                    log.info("closeChannel: begin close the channel[{}] Found: {}", addrRemote, prevCW != null);

                    if (null == prevCW) {
                        log.info("closeChannel: the channel[{}] has been removed from the channel table before", addrRemote);
                        removeItemFromTable = false;
                    } else if (prevCW.getChannel() != channel) {
                        log.info("closeChannel: the channel[{}] has been closed before, and has been created again, nothing to do.",
                                addrRemote);
                        removeItemFromTable = false;
                    }

                    if (removeItemFromTable) {
                        this.channelTables.remove(addrRemote);
                        log.info("closeChannel: the channel[{}] was removed from channel table", addrRemote);
                    }

                    RemotingUtil.closeChannel(channel);
                } catch (Exception e) {
                    log.error("closeChannel: close the channel exception", e);
                } finally {
                    this.lockChannelTables.unlock();
                }
            } else {
                log.warn("closeChannel: try to lock channel table, but timeout, {}ms", LOCK_TIMEOUT_MILLIS);
            }
        } catch (InterruptedException e) {
            log.error("closeChannel exception", e);
        }
    }

    /**
     * 关闭服务
     */
    public void shutdown() {
        log.info("shut down your server.");
        for (ChannelWrapper cw : this.channelTables.values()) {
            this.closeChannel(null, cw.getChannel());
        }
        eventLoopGroupWorker.shutdownGracefully();
    }
}
