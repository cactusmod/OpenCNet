package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn.core.CNetServer;
import com.dwarslooper.cactus.ocn.protocol.impl.ProtocolImpl;
import com.dwarslooper.cactus.ocn.core.CNetServerBuilder;
import com.dwarslooper.cactus.ocn.protocol.impl.builder.ProtocolBuilder;
import io.netty.channel.ChannelOption;

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
    }

}
