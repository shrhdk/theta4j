package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.EOFException;
import java.io.IOException;

/**
 * Data Packet defined in PTP-IP
 */
public final class DataPacket extends PtpIpPacket {
    private static final int MIN_SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES;

    private final UINT32 transactionID;
    private final byte[] dataPayload;

    private final byte[] payload;

    // Constructor

    public DataPacket(UINT32 transactionID, byte[] dataPayload) {
        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("dataPayload", dataPayload);

        this.transactionID = transactionID;
        this.dataPayload = dataPayload.clone();

        this.payload = ByteUtils.join(
                transactionID.bytes(),
                this.dataPayload
        );
    }

    // Static Factory Method

    public static DataPacket read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PacketUtils.assertType(type, Type.DATA);

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
