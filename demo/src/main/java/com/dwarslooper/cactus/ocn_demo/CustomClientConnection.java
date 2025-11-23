package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn_server.connection.AbstractClientConnection;
import com.dwarslooper.cactus.ocn_server.protocol.packet.IServerPacketIn;
import io.netty.channel.ChannelHandlerContext;

public class CustomClientConnection extends AbstractClientConnection {

    @Override
    public void connected(ChannelHandlerContext ctx) {

    }

    @Override
    public void disconnected(ChannelHandlerContext ctx) {

    }

    @Override
    public void exception(ChannelHandlerContext ctx, Throwable cause) {
        super.exception(ctx, cause);
    }

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, IServerPacketIn packet) {
        super.handle(channelHandlerContext, packet);
    }

    @Override
    public boolean shouldHandle(IServerPacketIn packet) {
        return super.shouldHandle(packet);
    }
}
