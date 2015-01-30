package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * EndData Packet defined in PTP-IP
 */
public final class EndDataPacket extends PtpIpPacket {
    private static final int MIN_SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES;

    private final UINT32 transactionID;
    private final byte[] dataPayload;

    // Constructor

    public EndDataPacket(UINT32 transactionID, byte[] dataPayload) {
        super(Type.END_DATA);

        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("dataPayload", dataPayload);

        this.transactionID = transactionID;
        this.dataPayload = dataPayload.clone();

        super.payload = ByteUtils.join(
                transactionID.bytes(),
                this.dataPayload
        );
    }

    // Getter

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public byte[] getDataPayload() {
        return dataPayload.clone();
    }

    // Static Factory Method

    public static EndDataPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.END_DATA);
        PacketUtils.checkMinLength((int) payloadLength, MIN_SIZE_IN_BYTES);

        long dataLength = payloadLength - UINT32.SIZE_IN_BYTES;              // -TransactionID

        UINT32 transactionID = pis.readUINT32();
        byte[] dataPayload = new byte[(int) dataLength];

        if (pis.read(dataPayload) == -1) {
            throw new IOException();
        }

        return new EndDataPacket(transactionID, dataPayload);
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

        EndDataPacket rhs = (EndDataPacket) o;

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
