/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;
import org.theta4j.util.Validators;

import java.io.EOFException;
import java.io.IOException;

/**
 * Data Packet defined in PTP-IP
 */
public final class DataPacket extends PtpIpPacket {
    private final UINT32 transactionID;
    private final byte[] dataPayload;

    private final byte[] payload;

    // Constructor

    public DataPacket(UINT32 transactionID, byte[] dataPayload) {
        Validators.notNull("transactionID", transactionID);
        Validators.notNull("dataPayload", dataPayload);

        this.transactionID = transactionID;
        this.dataPayload = dataPayload.clone();

        this.payload = ArrayUtils.join(
                transactionID.bytes(),
                this.dataPayload
        );
    }

    // Static Factory Method

    public static DataPacket read(PtpInputStream pis) throws IOException {
        Validators.notNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PtpIpPacketUtils.assertType(type, Type.DATA);

        // Read Body (TransactionID)
        UINT32 transactionID = pis.readUINT32();

        // Read Body (Data)
        long dataLength = payloadLength - UINT32.SIZE_IN_BYTES; // -TransactionID
        byte[] dataPayload = new byte[(int) dataLength];
        if (pis.read(dataPayload) != dataLength) {
            throw new EOFException();
        }

        return new DataPacket(transactionID, dataPayload);
    }

    // PtpIpPacket

    @Override
    Type getType() {
        return Type.DATA;
    }

    @Override
    byte[] getPayload() {
        return payload;
    }

    // Getter

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public byte[] getDataPayload() {
        return dataPayload.clone();
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

        DataPacket rhs = (DataPacket) o;

        return new EqualsBuilder()
                .append(transactionID, rhs.transactionID)
                .append(dataPayload, rhs.dataPayload)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(transactionID)
                .append(dataPayload)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("transactionID", transactionID)
                .append("dataPayload", dataPayload)
                .toString();
    }
}
