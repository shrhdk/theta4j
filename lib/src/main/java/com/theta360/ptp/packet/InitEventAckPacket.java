package com.theta360.ptp.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;

import java.io.IOException;

public final class InitEventAckPacket extends PtpIpPacket {
    private static final int SIZE = 0;

    public InitEventAckPacket() {
        super(Type.INIT_EVENT_ACK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Type.INIT_EVENT_ACK.ordinal();
    }

    @Override
    public String toString() {
        return "InitEventAckPacket{}";
    }

    public static InitEventAckPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - UINT32.SIZE - UINT32.SIZE;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.asseertType(type, Type.INIT_EVENT_ACK);
        PacketUtils.checkLength((int) payloadLength, SIZE);

        return new InitEventAckPacket();
    }
}
