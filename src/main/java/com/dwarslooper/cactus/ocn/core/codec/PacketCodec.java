package com.dwarslooper.cactus.ocn.core.codec;

import com.dwarslooper.cactus.ocn.protocol.impl.IPacketIn;
import com.dwarslooper.cactus.ocn.protocol.impl.IPacketOut;
import com.dwarslooper.cactus.ocn.protocol.IProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import java.util.List;

public class PacketCodec extends ByteToMessageCodec<IPacketOut> {

    private final IProtocol protocol;

    public PacketCodec(IProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IPacketOut packet, ByteBuf out) {
        int id = protocol.getPacketIdOut(packet);

        if(id < 0) {
            throw new EncoderException("Bad packet class: " + packet.getClass().getName());
        }

        out.writeByte(id);
        packet.write(out);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> outList) {
        if(in.readableBytes() > 0) {
            int id = in.readUnsignedByte();
            IPacketIn packet = protocol.createPacketIn(id, in);

            if(packet == null) {
                throw new DecoderException("Bad packet ID: " + id);
            }

            outList.add(packet);
        }
    }
}
