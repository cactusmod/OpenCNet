package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn_client.CNetClient;
import com.dwarslooper.cactus.ocn_client.builder.CNetClientBuilder;
import com.dwarslooper.cactus.ocn_commons.network.protocol.builder.ProtocolBuilder;
import com.dwarslooper.cactus.ocn_commons.network.protocol.impl.ProtocolImpl;
import com.dwarslooper.cactus.ocn_server.CNetServer;
import com.dwarslooper.cactus.ocn_server.builder.CNetServerBuilder;
import io.netty.channel.ChannelOption;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        ProtocolImpl protocol = new ProtocolBuilder()
                .addIn(0, NumberPacketIn.class)
                .addOut(0, NumberPacketOut.class)
                .build();

        CNetServer server = new CNetServerBuilder(1414, protocol)
                .timeout(20L)
                .withChannelConfig(config -> {
                    config.setOption(ChannelOption.SO_KEEPALIVE, true);
                })
                .useConnection(CustomClientConnection::new)
                .build();

        server.start();

        CNetClient client = new CNetClientBuilder(new InetSocketAddress("127.0.0.1", 1414), protocol)
                .timeout(20L)
                .withChannelConfig(config -> {
                    config.setOption(ChannelOption.SO_KEEPALIVE, true);
                })
                .useConnection(CustomClient::new)
                .build();

        client.connect();
    }

}
