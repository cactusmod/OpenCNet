package com.dwarslooper.cactus.ocn_client.builder;

import com.dwarslooper.cactus.ocn_client.CNetClient;
import com.dwarslooper.cactus.ocn_client.connection.AbstractClientConnection;
import com.dwarslooper.cactus.ocn_commons.network.codec.PacketCodec;
import com.dwarslooper.cactus.ocn_commons.network.codec.PacketSizer;
import com.dwarslooper.cactus.ocn_commons.network.protocol.IProtocol;
import io.netty.channel.*;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CNetClientBuilder {

	public static CNetClientBuilder create(InetSocketAddress address, IProtocol protocol) {
		return new CNetClientBuilder(address, protocol);
	}

	private final InetSocketAddress address;
	private final IProtocol protocol;

	private Consumer<ChannelConfig> channelConfig = config -> {};
	private Supplier<AbstractClientConnection> connectionSupplier = AbstractClientConnection::createDefault;
	private long timeout = 120L;

	public CNetClientBuilder(InetSocketAddress address, IProtocol protocol) {
		this.address = address;
		this.protocol = protocol;
	}

	public CNetClientBuilder timeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	public CNetClientBuilder useConnection(Supplier<AbstractClientConnection> connectionSupplier) {
		this.connectionSupplier = connectionSupplier;
		return this;
	}

	public CNetClientBuilder withChannelConfig(Consumer<ChannelConfig> config) {
		this.channelConfig = config;
		return this;
	}

	public CNetClient build() {
		return new CNetClient(address, new ChannelInitializer<>() {
			@Override
			protected void initChannel(Channel channel) {
				AbstractClientConnection connection = connectionSupplier.get();

				channel.config().setOption(ChannelOption.TCP_NODELAY, true);
				channelConfig.accept(channel.config());

				ChannelPipeline cp = channel.pipeline();
				cp.addLast(new ReadTimeoutHandler(timeout, TimeUnit.SECONDS));
				cp.addLast(new PacketSizer());
				cp.addLast(new PacketCodec(protocol));
				cp.addLast(connection);
			}
		});
	}

}
