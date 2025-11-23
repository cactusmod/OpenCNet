package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn_server.connection.AbstractClientConnection;
import com.dwarslooper.cactus.ocn_server.protocol.packet.IServerPacketIn;
import io.netty.buffer.ByteBuf;

public class NumberPacketIn implements IServerPacketIn {

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
