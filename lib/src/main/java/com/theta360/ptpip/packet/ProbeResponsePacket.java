package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * ProbeResponse Packet defined in PTP-IP
 */
public final class ProbeResponsePacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = 0;

    // Constructor

    public ProbeResponsePacket() {
        super(Type.PROBE_RESPONSE);
    }

    // Static Factory Method

    public static ProbeResponsePacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.PROBE_RESPONSE.value(), Type.PROBE_RESPONSE);
        PacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        return new ProbeResponsePacket();
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Type.PROBE_RESPONSE.ordinal();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
