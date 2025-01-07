package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn.core.AbstractClientConnection;
import com.dwarslooper.cactus.ocn.protocol.impl.IPacketIn;
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
    public void handle(ChannelHandlerContext channelHandlerContext, IPacketIn packet) {
        super.handle(channelHandlerContext, packet);
    }

    @Override
    public boolean shouldHandle(IPacketIn packet) {
        return super.shouldHandle(packet);
    }
}
