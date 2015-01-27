package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;

import java.io.IOException;

/**
 * ProbeRequest Packet defined in PTP-IP
 */
public final class ProbeRequestPacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = 0;

    // Constructor

    public ProbeRequestPacket() {
        super(Type.PROBE_REQUEST);
    }

    // Static Factory Method

    public static ProbeRequestPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.PROBE_REQUEST.value(), Type.PROBE_REQUEST);
        PacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        return new ProbeRequestPacket();
    }

    // Basic Method

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
}
