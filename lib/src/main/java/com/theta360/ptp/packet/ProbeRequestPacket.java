package com.theta360.ptp.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;

import java.io.IOException;

public final class ProbeRequestPacket extends PtpIpPacket {
    private static final int SIZE = 0;

    public ProbeRequestPacket() {
        super(Type.PROBE_REQUEST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Type.PROBE_REQUEST.ordinal();
    }

    @Override
    public String toString() {
        return "ProbeRequestPacket{}";
    }

    public static ProbeRequestPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - UINT32.SIZE - UINT32.SIZE;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.asseertType(type, Type.PROBE_REQUEST.getCode(), Type.PROBE_REQUEST);
        PacketUtils.checkLength((int) payloadLength, SIZE);

        return new ProbeRequestPacket();
    }
}
