package com.dwarslooper.cactus.ocn.core;

import com.dwarslooper.cactus.ocn.protocol.impl.IPacketIn;
import io.netty.channel.ChannelHandlerContext;

public interface IClientConnection {

    void connected(ChannelHandlerContext ctx);
    void disconnected(ChannelHandlerContext ctx);

    default void handle(ChannelHandlerContext channelHandlerContext, IPacketIn packet) {

    }

    default void exception(ChannelHandlerContext ctx, Throwable cause) {

    }

    default boolean shouldHandle(IPacketIn packet) {
        return true;
    }

}
