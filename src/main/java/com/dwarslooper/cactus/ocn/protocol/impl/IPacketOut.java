package com.dwarslooper.cactus.ocn.protocol.impl;

import com.dwarslooper.cactus.ocn.protocol.IPacket;
import io.netty.buffer.ByteBuf;

public interface IPacketOut extends IPacket {

    void write(ByteBuf buf);

}
