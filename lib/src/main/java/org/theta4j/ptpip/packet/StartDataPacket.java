/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.util.ArrayUtils;
import org.theta4j.util.Validators;

import java.io.IOException;

/**
 * StartData Packet defined in PTP-IP
 */
public final class StartDataPacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES + UINT64.SIZE_IN_BYTES;

    private final UINT32 transactionID;
    private final UINT64 totalDataLength;

    private final byte[] payload;

    // Constructor

    public StartDataPacket(UINT32 transactionID, UINT64 totalDataLength) {
        Validators.notNull("transactionID", transactionID);
        Validators.notNull("totalDataLength", totalDataLength);

        this.transactionID = transactionID;
        this.totalDataLength = totalDataLength;

        this.payload = ArrayUtils.join(
                transactionID.bytes(),
                totalDataLength.bytes()
        );
    }

    // Static Factory Method

    public static StartDataPacket read(PtpInputStream pis) throws IOException {
        Validators.notNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PtpIpPacketUtils.assertType(type, Type.START_DATA);
        PtpIpPacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        // Read Body
        UINT32 transactionID = pis.readUINT32();
        UINT64 totalDataLength = pis.readUINT64();

        return new StartDataPacket(transactionID, totalDataLength);
    }

    // PtpIpPacket

    @Override
    Type getType() {
        return Type.START_DATA;
    }

    @Override
    byte[] getPayload() {
        return payload;
    }

    // Getter

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public UINT64 getTotalDataLength() {
        return totalDataLength;
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

        StartDataPacket rhs = (StartDataPacket) o;

        return new EqualsBuilder()
                .append(transactionID, rhs.transactionID)
                .append(totalDataLength, rhs.totalDataLength)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(transactionID)
                .append(totalDataLength)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("transactionID", transactionID)
                .append("totalDataLength", totalDataLength)
                .toString();
    }
}
