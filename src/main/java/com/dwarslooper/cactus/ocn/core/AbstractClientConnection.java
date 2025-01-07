package com.dwarslooper.cactus.ocn.core;

import com.dwarslooper.cactus.ocn.protocol.impl.IPacketIn;
import com.dwarslooper.cactus.ocn.protocol.impl.IPacketOut;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class AbstractClientConnection extends SimpleChannelInboundHandler<IPacketIn> implements IClientConnection {

    private Channel channel;

    @Override
    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channel = ctx.channel();
        connected(ctx);
    }

    @Override
    public final void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        disconnected(ctx);
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        exception(ctx, cause);
    }

    @Override
    protected final void channelRead0(ChannelHandlerContext channelHandlerContext, IPacketIn iPacketIn) {
        if(shouldHandle(iPacketIn)) {
            iPacketIn.handle(this);
        }
    }

    public void sendPacket(IPacketOut packet) {
        channel.writeAndFlush(packet);
    }

    public static AbstractClientConnection createDefault() {
        return new AbstractClientConnection() {
            @Override
            public void connected(ChannelHandlerContext ctx) {

            }

            @Override
            public void disconnected(ChannelHandlerContext ctx) {

            }

            @Override
            public void handle(ChannelHandlerContext channelHandlerContext, IPacketIn packet) {
                throw new UnsupportedOperationException("Handling on connection class is unsupported in default configuration");
            }
        };
    }

}
