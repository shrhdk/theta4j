package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;
import java.util.Arrays;

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

    public static EndDataPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.END_DATA, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkMinLength(MIN_SIZE, buffer.remaining());

        // Get Transaction ID
        byte[] transactionIDBytes = new byte[UINT32.SIZE];
        buffer.get(transactionIDBytes);
        UINT32 transactionID = new UINT32(transactionIDBytes);

        // Get Data Payload
        byte[] dataPayload = new byte[buffer.remaining()];
        buffer.get(dataPayload);

        return new EndDataPacket(transactionID, dataPayload);
    }
}
