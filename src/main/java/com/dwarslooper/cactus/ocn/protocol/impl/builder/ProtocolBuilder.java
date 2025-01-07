package com.dwarslooper.cactus.ocn.protocol.impl.builder;

import com.dwarslooper.cactus.ocn.protocol.impl.IPacketIn;
import com.dwarslooper.cactus.ocn.protocol.impl.IPacketOut;
import com.dwarslooper.cactus.ocn.protocol.impl.ProtocolImpl;
import io.netty.util.collection.IntObjectHashMap;

import java.util.HashMap;
import java.util.Map;

public class ProtocolBuilder {

    private final Map<Integer, Class<? extends IPacketIn>> idToPacketIn = new IntObjectHashMap<>();
    private final Map<Class<? extends IPacketOut>, Integer> packetIdRegistry = new HashMap<>();

    public ProtocolBuilder() {

    }

    public ProtocolBuilder addIn(int id, Class<? extends IPacketIn> clazz) {
        idToPacketIn.put(id, clazz);
        return this;
    }

    public ProtocolBuilder addOut(int id, Class<? extends IPacketOut> clazz) {
        packetIdRegistry.put(clazz, id);
        return this;
    }

    public ProtocolImpl build() {
        return new ProtocolImpl(idToPacketIn, packetIdRegistry);
    }
}
