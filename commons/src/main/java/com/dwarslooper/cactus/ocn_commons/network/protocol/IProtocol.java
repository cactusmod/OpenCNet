package com.dwarslooper.cactus.ocn_commons.network.protocol;

import com.dwarslooper.cactus.ocn_commons.network.protocol.packet.IPacketIn;
import com.dwarslooper.cactus.ocn_commons.network.protocol.packet.IPacketOut;
import io.netty.buffer.ByteBuf;

public interface IProtocol {

    int getPacketIdOut(Class<? extends IPacketOut> packet);
    IPacketIn createPacketIn(int id, ByteBuf buf);

}
