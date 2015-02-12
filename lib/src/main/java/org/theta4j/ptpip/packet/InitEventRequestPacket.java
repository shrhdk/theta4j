package org.theta4j.ptpip.packet;

import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * InitEventRequest Packet defined in PTP-IP
 */
public final class InitEventRequestPacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES;

    private final UINT32 connectionNumber;

    private final byte[] payload;

    // Constructor

    public InitEventRequestPacket(UINT32 connectionNumber) {
        Validators.validateNonNull("connectionNumber", connectionNumber);

        this.connectionNumber = connectionNumber;
        this.payload = connectionNumber.bytes();
    }

    // Static Factory Method

    public static InitEventRequestPacket read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PtpIpPacketUtils.assertType(type, Type.INIT_EVENT_REQUEST);
        PtpIpPacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        // Read Body
        UINT32 connectionNumber = pis.readUINT32();

        return new InitEventRequestPacket(connectionNumber);
    }

    // PtpIpPacket

    @Override
    Type getType() {
        return Type.INIT_EVENT_REQUEST;
    }

    @Override
    byte[] getPayload() {
        return payload;
    }

    // Getter

    public UINT32 getConnectionNumber() {
        return connectionNumber;
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

        InitEventRequestPacket rhs = (InitEventRequestPacket) o;

        return connectionNumber.equals(rhs.connectionNumber);

    }

    @Override
    public int hashCode() {
        return connectionNumber.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("connectionNumber", connectionNumber)
                .toString();
    }
}
