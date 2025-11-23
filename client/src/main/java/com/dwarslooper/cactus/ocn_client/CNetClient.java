package com.dwarslooper.cactus.ocn_client;

import com.dwarslooper.cactus.ocn_commons.network.CommonNettyApplication;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

@ChannelHandler.Sharable
public class CNetClient extends CommonNettyApplication {

	public static final Logger LOGGER = LogManager.getLogger(CNetClient.class);

	private CNetClientStatus status;

	private Channel channel = null;

	public CNetClient(InetSocketAddress address, ChannelInitializer<?> initializer) {
		super(address, initializer);
	}

	public void connect() {
		disconnect();

		Bootstrap bootstrap = new Bootstrap()
				.channel(NioSocketChannel.class)
				.handler(getInitializer())
				.group(getLoopGroup());

		try {
			bootstrap.connect(getAddress()).syncUninterruptibly();
			status = CNetClientStatus.CONNECTED;
		} catch (Exception e) {
			LOGGER.error("Something went wrong with the backend while connecting", e);
		}
	}

	public void disconnect() {
		if(channel != null) {
			if(channel.isOpen()) {
				channel.disconnect();
				channel.close();
			}

			channel = null;
		}

		status = CNetClientStatus.DISCONNECTED;
	}

	public CNetClientStatus getStatus() {
		return status;
	}
}
