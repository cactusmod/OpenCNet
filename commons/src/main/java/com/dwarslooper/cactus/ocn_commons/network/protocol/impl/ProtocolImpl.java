package com.dwarslooper.cactus.ocn_commons.network.protocol.impl;

import com.dwarslooper.cactus.ocn_commons.network.protocol.IProtocol;
import com.dwarslooper.cactus.ocn_commons.network.protocol.packet.IPacketIn;
import com.dwarslooper.cactus.ocn_commons.network.protocol.packet.IPacketOut;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;

public class ProtocolImpl implements IProtocol {

    /** This will create an empty protocol, which will not accept any packets **/
    public static IProtocol empty() {
        return new ProtocolImpl(Collections.emptyMap(), Collections.emptyMap());
    }

    private final Map<Integer, Class<? extends IPacketIn>> idToPacketIn;
    private final Map<Class<? extends IPacketOut>, Integer> packetIdRegistry;

    public ProtocolImpl(Map<Integer, Class<? extends IPacketIn>> packetBuilders, Map<Class<? extends IPacketOut>, Integer> packetIdRegistry) {
        this.idToPacketIn = packetBuilders;
        this.packetIdRegistry = packetIdRegistry;
    }

    @Override
    public int getPacketIdOut(Class<? extends IPacketOut> packet) {
        return packetIdRegistry.get(packet);
    }

    @Override
    public IPacketIn createPacketIn(int id, ByteBuf buf) {
        if(idToPacketIn.containsKey(id)) {
            Class<? extends IPacketIn> packetClass = idToPacketIn.get(id);
            try {
                IPacketIn packet = packetClass.getDeclaredConstructor().newInstance();
                packet.read(buf);
                return packet;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Unable to create packet %s, no matching constructor found".formatted(packetClass.getCanonicalName()), e);
            }
        }

        return null;
    }

}
