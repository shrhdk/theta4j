package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class CancelPacket extends PtpIpPacket {
    private static final int SIZE = UINT32.SIZE;

    private final UINT32 transactionID;

    public CancelPacket(UINT32 transactionID) {
        super(Type.CANCEL);

        Validators.validateNonNull("transactionID", transactionID);

        this.transactionID = transactionID;

        super.payload = this.transactionID.bytes();
    }

    public UINT32 getTransactionID() {
        return transactionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CancelPacket that = (CancelPacket) o;

        if (!transactionID.equals(that.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return transactionID.hashCode();
    }

    @Override
    public String toString() {
        return "CancelPacket{" +
                "transactionID=" + transactionID +
                '}';
    }

    public static CancelPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.CANCEL, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkLength(SIZE, buffer.remaining());

        // Get Transaction ID
        byte[] transactionIDBytes = new byte[UINT32.SIZE];
        buffer.get(transactionIDBytes);
        UINT32 transactionID = new UINT32(transactionIDBytes);

        return new CancelPacket(transactionID);
    }
}
