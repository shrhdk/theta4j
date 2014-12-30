package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class StartDataPacket extends PtpIpPacket {
    private static final int SIZE = UINT32.SIZE + UINT64.SIZE;

    private final UINT32 transactionID;
    private final UINT64 totalDataLength;

    public StartDataPacket(UINT32 transactionID, UINT64 totalDataLength) {
        super(Type.START_DATA);

        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("totalDataLength", totalDataLength);

        this.transactionID = transactionID;
        this.totalDataLength = totalDataLength;

        super.payload = ByteUtils.join(
                transactionID.bytes(),
                totalDataLength.bytes()
        );
    }

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public UINT64 getTotalDataLength() {
        return totalDataLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartDataPacket that = (StartDataPacket) o;

        if (!totalDataLength.equals(that.totalDataLength)) return false;
        if (!transactionID.equals(that.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transactionID.hashCode();
        result = 31 * result + totalDataLength.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StartDataPacket{" +
                "transactionID=" + transactionID +
                ", totalDataLength=" + totalDataLength +
                '}';
    }

    public static StartDataPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.START_DATA, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkLength(SIZE, buffer.remaining());

        // Get Transaction ID
        byte[] transactionIDBytes = new byte[UINT32.SIZE];
        buffer.get(transactionIDBytes);
        UINT32 transactionID = new UINT32(transactionIDBytes);

        // Get Total Data Length
        byte[] totalDataLengthBytes = new byte[UINT64.SIZE];
        buffer.get(totalDataLengthBytes);
        UINT64 totalDataLength = new UINT64(totalDataLengthBytes);

        return new StartDataPacket(transactionID, totalDataLength);
    }
}
