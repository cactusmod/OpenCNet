package com.dwarslooper.cactus.ocn_commons.network.protocol.packet;

import io.netty.buffer.ByteBuf;

public interface IPacketIn extends IPacket {

    void read(ByteBuf buf);

}
