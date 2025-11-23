package com.dwarslooper.cactus.ocn_server.connection;

import com.dwarslooper.cactus.ocn_commons.network.IConnection;
import com.dwarslooper.cactus.ocn_server.protocol.packet.IServerPacketIn;
import com.dwarslooper.cactus.ocn_server.protocol.packet.IServerPacketOut;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class AbstractServerClientConnection extends SimpleChannelInboundHandler<IServerPacketIn> implements IConnection<IServerPacketIn> {

    private Channel channel;

    @Override
    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        connected(ctx);
        super.channelActive(ctx);
    }

    @Override
    public final void channelInactive(ChannelHandlerContext ctx) throws Exception {
        disconnected(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        exception(ctx, cause);
    }

    @Override
    protected final void channelRead0(ChannelHandlerContext channelHandlerContext, IServerPacketIn packet) {
        if(shouldHandle(packet)) {
            handle(channelHandlerContext, packet);
        }
    }

    public void sendPacket(IServerPacketOut packet) {
        channel.writeAndFlush(packet);
    }

    public static AbstractServerClientConnection createDefault() {
        return new AbstractServerClientConnection() {
            @Override
            public void connected(ChannelHandlerContext ctx) {

            }

            @Override
            public void disconnected(ChannelHandlerContext ctx) {

            }

            @Override
            public void handle(ChannelHandlerContext channelHandlerContext, IServerPacketIn packet) {
                throw new UnsupportedOperationException("Handling on connection class is unsupported in default configuration");
            }
        };
    }

}
