package com.dwarslooper.cactus.ocn_server.protocol.packet;

import com.dwarslooper.cactus.ocn_server.connection.IClientConnection;

public interface IServerPreProcessing {

	default void preProcess(IClientConnection connection) {

	}

}
