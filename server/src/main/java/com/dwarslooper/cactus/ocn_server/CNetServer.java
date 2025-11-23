package com.dwarslooper.cactus.ocn_server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

public class CNetServer {

    public static final Logger LOGGER = LogManager.getLogger(CNetServer.class);

    private EventLoopGroup loopGroup;
    private Channel channel;

    private final InetSocketAddress address;
    private final ChannelInitializer<?> initializer;

    public CNetServer(InetSocketAddress address, ChannelInitializer<?> initializer) {
        this.address = address;
        this.initializer = initializer;
    }

    public void start() {
        if(channel != null && channel.isOpen()) {
            return;
        }

        if(loopGroup == null) {
            loopGroup = new NioEventLoopGroup();
        }

        ChannelFuture future = new ServerBootstrap().channel(NioServerSocketChannel.class).childHandler(initializer).group(loopGroup).localAddress(address).bind();

        try {
            future.sync();
        } catch (InterruptedException ignore) {}
        channel = future.channel();

        LOGGER.info("Started! Hello, world!");
    }

    public void shutdown() {
        loopGroup.shutdownGracefully();
        channel.close();
    }
}
