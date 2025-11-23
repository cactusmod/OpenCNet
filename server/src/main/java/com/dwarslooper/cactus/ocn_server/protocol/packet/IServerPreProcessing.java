package com.dwarslooper.cactus.ocn_server.protocol.packet;

import com.dwarslooper.cactus.ocn_commons.network.IConnection;

public interface IServerPreProcessing {

	default void preProcess(IConnection connection) {

	}

}
