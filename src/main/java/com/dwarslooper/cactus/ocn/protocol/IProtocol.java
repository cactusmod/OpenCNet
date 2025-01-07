package com.dwarslooper.cactus.ocn.protocol;

import com.dwarslooper.cactus.ocn.protocol.impl.IPacketIn;
import com.dwarslooper.cactus.ocn.protocol.impl.IPacketOut;
import io.netty.buffer.ByteBuf;

public interface IProtocol {

    int getPacketIdOut(Class<? extends IPacketOut> packet);
    IPacketIn createPacketIn(int id, ByteBuf buf);

}
