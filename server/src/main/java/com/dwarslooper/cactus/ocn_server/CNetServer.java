package com.dwarslooper.cactus.ocn_server;

import com.dwarslooper.cactus.ocn_commons.network.CommonNettyApplication;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

public class CNetServer extends CommonNettyApplication {

    public static final Logger LOGGER = LogManager.getLogger(CNetServer.class);

    public CNetServer(InetSocketAddress address, ChannelInitializer<?> initializer) {
        super(address, initializer);
    }

    public void start() {
        if(channel != null && channel.isOpen()) {
            return;
        }

        ChannelFuture future = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .childHandler(getInitializer())
                .group(getLoopGroup())
                .localAddress(getAddress())
                .bind();

        try {
            future.sync();
        } catch (InterruptedException ignore) {}
        channel = future.channel();

        LOGGER.info("Started! Hello, world!");
    }

    public void shutdown() {
        getLoopGroup().shutdownGracefully();
        channel.close();
    }
}
