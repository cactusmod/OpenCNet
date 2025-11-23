package com.dwarslooper.cactus.ocn_demo;

import com.dwarslooper.cactus.ocn_client.connection.AbstractClientConnection;
import com.dwarslooper.cactus.ocn_client.protocol.packet.IClientPacketIn;
import io.netty.channel.ChannelHandlerContext;

public class CustomClient extends AbstractClientConnection {

	@Override
	public void connected(ChannelHandlerContext ctx) {
		System.out.println("client connected! hello, world!");
	}

	@Override
	public void disconnected(ChannelHandlerContext ctx) {
		System.out.println("client disconnected!");
	}

	@Override
	public void handle(ChannelHandlerContext channelHandlerContext, IClientPacketIn packet) {
		packet.handle(this);
	}
}
