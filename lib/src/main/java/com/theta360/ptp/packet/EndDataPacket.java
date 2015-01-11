package com.theta360.ptp.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.io.IOException;
import java.util.Arrays;

/**
 * EndData Packet defined in PTP-IP
 */
public final class EndDataPacket extends PtpIpPacket {
    private static final int MIN_SIZE = UINT32.SIZE;

    private final UINT32 transactionID;
    private final byte[] dataPayload;

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

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public byte[] getDataPayload() {
        return dataPayload.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndDataPacket that = (EndDataPacket) o;

        if (!Arrays.equals(dataPayload, that.dataPayload)) return false;
        if (!transactionID.equals(that.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transactionID.hashCode();
        result = 31 * result + Arrays.hashCode(dataPayload);
        return result;
    }

    @Override
    public String toString() {
        return "EndDataPacket{" +
                "transactionID=" + transactionID +
                ", dataPayload=" + Arrays.toString(dataPayload) +
                '}';
    }

    public static EndDataPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - UINT32.SIZE - UINT32.SIZE;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.END_DATA);
        PacketUtils.checkMinLength((int) payloadLength, MIN_SIZE);

        long dataLength = payloadLength - UINT32.SIZE;              // -TransactionID

        UINT32 transactionID = pis.readUINT32();
        byte[] dataPayload = new byte[(int) dataLength];

        if (pis.read(dataPayload) == -1) {
            throw new IOException();
        }

        return new EndDataPacket(transactionID, dataPayload);
    }
}
