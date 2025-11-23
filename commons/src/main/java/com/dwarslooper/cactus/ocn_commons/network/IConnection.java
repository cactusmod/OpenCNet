package com.dwarslooper.cactus.ocn_commons.network;

import com.dwarslooper.cactus.ocn_commons.network.protocol.packet.IPacketIn;
import io.netty.channel.ChannelHandlerContext;

public interface IConnection<T extends IPacketIn> {

    void connected(ChannelHandlerContext ctx);
    void disconnected(ChannelHandlerContext ctx);

    default void handle(ChannelHandlerContext channelHandlerContext, T packet) {

    }

    default void exception(ChannelHandlerContext ctx, Throwable cause) {

    }

    default boolean shouldHandle(T packet) {
        return true;
    }

}
