package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * InitEventAck Packet defined in PTP-IP
 */
public final class InitEventAckPacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = 0;

    // Constructor

    public InitEventAckPacket() {
        super(Type.INIT_EVENT_ACK);
    }

    // Static Factory Method

    public static InitEventAckPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.INIT_EVENT_ACK);
        PacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        return new InitEventAckPacket();
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
        return Type.INIT_EVENT_ACK.ordinal();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
