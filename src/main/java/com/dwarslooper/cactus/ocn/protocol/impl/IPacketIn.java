package com.dwarslooper.cactus.ocn.protocol.impl;

import com.dwarslooper.cactus.ocn.core.AbstractClientConnection;
import com.dwarslooper.cactus.ocn.protocol.IPacket;
import io.netty.buffer.ByteBuf;

public interface IPacketIn extends IPacket {

    void read(ByteBuf buf);
    void handle(AbstractClientConnection connection);

}
