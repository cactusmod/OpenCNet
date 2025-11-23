package com.dwarslooper.cactus.ocn_client.protocol.packet;

import com.dwarslooper.cactus.ocn_client.connection.AbstractClientConnection;
import com.dwarslooper.cactus.ocn_commons.network.protocol.packet.IPacketIn;

public interface IClientPacketIn extends IPacketIn {

	void handle(AbstractClientConnection connection);

}
