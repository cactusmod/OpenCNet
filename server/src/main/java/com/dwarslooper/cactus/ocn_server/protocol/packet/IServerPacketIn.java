package com.dwarslooper.cactus.ocn_server.protocol.packet;

import com.dwarslooper.cactus.ocn_commons.network.protocol.packet.IPacketIn;
import com.dwarslooper.cactus.ocn_server.connection.AbstractClientConnection;

public interface IServerPacketIn extends IPacketIn, IServerPreProcessing {

	void handle(AbstractClientConnection connection);

}
