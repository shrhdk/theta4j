/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

import java.io.IOException;

/**
 * Cancel Packet defined in PTP-IP
 */
public final class CancelPacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES;

    private final UINT32 transactionID;

    private final byte[] payload;

    // Constructor

    public CancelPacket(UINT32 transactionID) {
        Validators.notNull("transactionID", transactionID);

        this.transactionID = transactionID;

        this.payload = this.transactionID.bytes();
    }

    // Static Factory Method

    public static CancelPacket read(PtpInputStream pis) throws IOException {
        Validators.notNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PtpIpPacketUtils.assertType(type, Type.CANCEL);
        PtpIpPacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        // Read Body
        UINT32 transactionID = pis.readUINT32();

        return new CancelPacket(transactionID);
    }

    // PtpIpPacket

    @Override
    Type getType() {
        return Type.CANCEL;
    }

    @Override
    byte[] getPayload() {
        return payload;
    }

    // Getter

    public UINT32 getTransactionID() {
        return transactionID;
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

        CancelPacket rhs = (CancelPacket) o;

        return transactionID.equals(rhs.transactionID);

    }

    @Override
    public int hashCode() {
        return transactionID.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("transactionID", transactionID)
                .toString();
    }
}
