package com.dwarslooper.cactus.ocn_commons.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

public abstract class CommonNettyApplication {

	private final EventLoopGroup loopGroup = new NioEventLoopGroup();

	private final InetSocketAddress address;
	private final ChannelInitializer<?> initializer;

	protected Channel channel;

	public CommonNettyApplication(InetSocketAddress address, ChannelInitializer<?> initializer) {
		this.address = address;
		this.initializer = initializer;
	}

	public EventLoopGroup getLoopGroup() {
		return loopGroup;
	}

	public InetSocketAddress getAddress() {
		return address;
	}

	public ChannelInitializer<?> getInitializer() {
		return initializer;
	}

}
