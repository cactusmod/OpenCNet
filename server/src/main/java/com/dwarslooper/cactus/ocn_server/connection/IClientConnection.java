package com.dwarslooper.cactus.ocn_server.connection;

import com.dwarslooper.cactus.ocn_server.protocol.packet.IServerPacketIn;
import io.netty.channel.ChannelHandlerContext;

public interface IClientConnection {

    void connected(ChannelHandlerContext ctx);
    void disconnected(ChannelHandlerContext ctx);

    default void handle(ChannelHandlerContext channelHandlerContext, IServerPacketIn packet) {

    }

    default void exception(ChannelHandlerContext ctx, Throwable cause) {

    }

    default boolean shouldHandle(IServerPacketIn packet) {
        return true;
    }

}
