package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn.core.AbstractClientConnection;
import com.dwarslooper.cactus.ocn.core.CNetServer;
import com.dwarslooper.cactus.ocn.protocol.impl.ProtocolImpl;
import com.dwarslooper.cactus.ocn.protocol.impl.builder.CNetServerBuilder;
import com.dwarslooper.cactus.ocn.protocol.impl.builder.ProtocolBuilder;

public class Main {

    CNetServer server = new CNetServerBuilder(
            1414,
            new ProtocolBuilder()
                    .build()
    ).build();

}
