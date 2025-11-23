package com.dwarslooper.cactus.ocn_client.connection;

import com.dwarslooper.cactus.ocn_client.protocol.packet.IClientPacketIn;
import com.dwarslooper.cactus.ocn_client.protocol.packet.IClientPacketOut;
import com.dwarslooper.cactus.ocn_commons.network.IConnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class AbstractClientConnection extends SimpleChannelInboundHandler<IClientPacketIn> implements IConnection<IClientPacketIn> {

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
	protected final void channelRead0(ChannelHandlerContext channelHandlerContext, IClientPacketIn packet) {
		if(shouldHandle(packet)) {
			handle(channelHandlerContext, packet);
		}
	}

	public void sendPacket(IClientPacketOut packet) {
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
			public void handle(ChannelHandlerContext channelHandlerContext, IClientPacketIn packet) {
				throw new UnsupportedOperationException("Handling on connection class is unsupported in default configuration");
			}
		};
	}

}
