package com.theta360.ptp.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.io.IOException;

/**
 * StartData Packet defined in PTP-IP
 */
public final class StartDataPacket extends PtpIpPacket {
    private static final int SIZE = UINT32.SIZE + UINT64.SIZE;

    private final UINT32 transactionID;
    private final UINT64 totalDataLength;

    // Constructor

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

    // Getter

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public UINT64 getTotalDataLength() {
        return totalDataLength;
    }

    // Static Factory Method

    public static StartDataPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - UINT32.SIZE - UINT32.SIZE;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.START_DATA);
        PacketUtils.checkLength((int) payloadLength, SIZE);

        UINT32 transactionID = pis.readUINT32();
        UINT64 totalDataLength = pis.readUINT64();

        return new StartDataPacket(transactionID, totalDataLength);
    }

    // Basic Method

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
}
