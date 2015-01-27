package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.io.IOException;

/**
 * Cancel Packet defined in PTP-IP
 */
public final class CancelPacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES;

    private final UINT32 transactionID;

    // Constructor

    public CancelPacket(UINT32 transactionID) {
        super(Type.CANCEL);

        Validators.validateNonNull("transactionID", transactionID);

        this.transactionID = transactionID;

        super.payload = this.transactionID.bytes();
    }

    // Getter

    public UINT32 getTransactionID() {
        return transactionID;
    }

    // Static Factory Method

    public static CancelPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - UINT32.SIZE_IN_BYTES - UINT32.SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.CANCEL);
        PacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        UINT32 transactionID = pis.readUINT32();

        return new CancelPacket(transactionID);
    }

    // Basic Method

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
}
