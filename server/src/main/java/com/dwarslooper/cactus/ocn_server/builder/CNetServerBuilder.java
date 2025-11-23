package com.dwarslooper.cactus.ocn_server.builder;

import com.dwarslooper.cactus.ocn_commons.network.codec.PacketCodec;
import com.dwarslooper.cactus.ocn_commons.network.codec.PacketSizer;
import com.dwarslooper.cactus.ocn_commons.network.protocol.IProtocol;
import com.dwarslooper.cactus.ocn_server.connection.AbstractClientConnection;
import com.dwarslooper.cactus.ocn_server.CNetServer;
import io.netty.channel.*;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CNetServerBuilder {

    public static CNetServerBuilder create(int port, IProtocol protocol) {
        return new CNetServerBuilder(port, protocol);
    }

    private final int port;
    private final IProtocol protocol;

    private Consumer<ChannelConfig> channelConfig = config -> {};
    private Supplier<AbstractClientConnection> connectionSupplier = AbstractClientConnection::createDefault;
    private long timeout = 120L;

    public CNetServerBuilder(int port, IProtocol protocol) {
        this.port = port;
        this.protocol = protocol;
    }

    public CNetServerBuilder timeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public CNetServerBuilder useConnection(Supplier<AbstractClientConnection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
        return this;
    }

    public CNetServerBuilder withChannelConfig(Consumer<ChannelConfig> config) {
        this.channelConfig = config;
        return this;
    }

    public CNetServer build() {
        return new CNetServer(new InetSocketAddress("0.0.0.0", port), new ChannelInitializer<>() {
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
