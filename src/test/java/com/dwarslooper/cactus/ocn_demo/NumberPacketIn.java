package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn.core.AbstractClientConnection;
import com.dwarslooper.cactus.ocn.protocol.impl.IPacketIn;
import io.netty.buffer.ByteBuf;

public class NumberPacketIn implements IPacketIn {

    private int number;

    @Override
    public void read(ByteBuf buf) {
        // Here we receive a raw ByteBuf, from which we can read information contained in this packet.
        this.number = buf.readInt();
    }

    @Override
    public void handle(AbstractClientConnection connection) {
        System.out.println("Number received: " + number);
    }

}
