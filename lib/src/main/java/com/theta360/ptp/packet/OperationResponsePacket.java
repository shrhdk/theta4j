package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class OperationResponsePacket extends PtpIpPacket {
    private static final int SIZE = UINT16.SIZE + UINT32.SIZE + UINT32.SIZE * 5;

    private final UINT16 responseCode;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3, p4, p5;

    public OperationResponsePacket(UINT16 responseCode, UINT32 transactionID) {
        this(responseCode, transactionID, new UINT32(0));
    }

    public OperationResponsePacket(UINT16 responseCode, UINT32 transactionID, UINT32 p1) {
        this(responseCode, transactionID, p1, new UINT32(0));
    }

    public OperationResponsePacket(UINT16 responseCode, UINT32 transactionID, UINT32 p1, UINT32 p2) {
        this(responseCode, transactionID, p1, p2, new UINT32(0));
    }

    public OperationResponsePacket(UINT16 responseCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3) {
        this(responseCode, transactionID, p1, p2, p3, new UINT32(0));
    }

    public OperationResponsePacket(UINT16 responseCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) {
        this(responseCode, transactionID, p1, p2, p3, p4, new UINT32(0));
    }

    public OperationResponsePacket(UINT16 responseCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) {
        super(Type.OPERATION_RESPONSE);

        Validators.validateNonNull("responseCode", responseCode);
        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("p1", p1);
        Validators.validateNonNull("p2", p2);
        Validators.validateNonNull("p3", p3);
        Validators.validateNonNull("p4", p4);
        Validators.validateNonNull("p5", p5);

        this.responseCode = responseCode;
        this.transactionID = transactionID;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;

        super.payload = ByteUtils.join(
                responseCode.bytes(),
                transactionID.bytes(),
                p1.bytes(), p2.bytes(), p3.bytes(), p4.bytes(), p5.bytes()
        );
    }

    public UINT16 getResponseCode() {
        return responseCode;
    }

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public UINT32 getP1() {
        return p1;
    }

    public UINT32 getP2() {
        return p2;
    }

    public UINT32 getP3() {
        return p3;
    }

    public UINT32 getP4() {
        return p4;
    }

    public UINT32 getP5() {
        return p5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationResponsePacket that = (OperationResponsePacket) o;

        if (!p1.equals(that.p1)) return false;
        if (!p2.equals(that.p2)) return false;
        if (!p3.equals(that.p3)) return false;
        if (!p4.equals(that.p4)) return false;
        if (!p5.equals(that.p5)) return false;
        if (!responseCode.equals(that.responseCode)) return false;
        if (!transactionID.equals(that.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = responseCode.hashCode();
        result = 31 * result + transactionID.hashCode();
        result = 31 * result + p1.hashCode();
        result = 31 * result + p2.hashCode();
        result = 31 * result + p3.hashCode();
        result = 31 * result + p4.hashCode();
        result = 31 * result + p5.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OperationResponsePacket{" +
                "responseCode=" + responseCode +
                ", transactionID=" + transactionID +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                '}';
    }

    public static OperationResponsePacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.OPERATION_RESPONSE, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkLength(SIZE, buffer.remaining());

        // Get Response Code
        byte[] responseCodeBytes = new byte[UINT16.SIZE];
        buffer.get(responseCodeBytes);
        UINT16 responseCode = new UINT16(responseCodeBytes);

        // Get Transaction ID
        byte[] transactionIDBytes = new byte[UINT32.SIZE];
        buffer.get(transactionIDBytes);
        UINT32 transactionID = new UINT32(transactionIDBytes);

        // Get P1
        byte[] p1Bytes = new byte[UINT32.SIZE];
        buffer.get(p1Bytes);
        UINT32 p1 = new UINT32(p1Bytes);

        // Get P2
        byte[] p2Bytes = new byte[UINT32.SIZE];
        buffer.get(p2Bytes);
        UINT32 p2 = new UINT32(p2Bytes);

        // Get P3
        byte[] p3Bytes = new byte[UINT32.SIZE];
        buffer.get(p3Bytes);
        UINT32 p3 = new UINT32(p3Bytes);

        // Get P4
        byte[] p4Bytes = new byte[UINT32.SIZE];
        buffer.get(p4Bytes);
        UINT32 p4 = new UINT32(p4Bytes);

        // Get P5
        byte[] p5Bytes = new byte[UINT32.SIZE];
        buffer.get(p5Bytes);
        UINT32 p5 = new UINT32(p5Bytes);

        return new OperationResponsePacket(responseCode, transactionID, p1, p2, p3, p4, p5);
    }
}
