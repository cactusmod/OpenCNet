package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn.protocol.impl.IPacketOut;
import io.netty.buffer.ByteBuf;

public class NumberPacketOut implements IPacketOut {

    private final int number;

    public NumberPacketOut(int number) {
        this.number = number;
    }

    @Override
    public void write(ByteBuf buf) {
        // Write the number to the ByteBuf
        buf.writeInt(this.number);
    }

}
