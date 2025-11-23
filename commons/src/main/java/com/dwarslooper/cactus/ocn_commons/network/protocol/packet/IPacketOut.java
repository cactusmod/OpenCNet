package com.dwarslooper.cactus.ocn_commons.network.protocol.packet;

import io.netty.buffer.ByteBuf;

public interface IPacketOut extends IPacket {

    void write(ByteBuf buf);

}
