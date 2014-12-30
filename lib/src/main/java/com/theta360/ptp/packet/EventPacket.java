package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class EventPacket extends PtpIpPacket {
    private static final int SIZE = UINT16.SIZE + UINT32.SIZE + UINT32.SIZE * 3;

    private final UINT16 eventCode;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3;

    public EventPacket(UINT16 eventCode, UINT32 transactionID) {
        this(eventCode, transactionID, new UINT32(0));
    }

    public EventPacket(UINT16 eventCode, UINT32 transactionID, UINT32 p1) {
        this(eventCode, transactionID, p1, new UINT32(0));
    }

    public EventPacket(UINT16 eventCode, UINT32 transactionID, UINT32 p1, UINT32 p2) {
        this(eventCode, transactionID, p1, p2, new UINT32(0));
    }

    public EventPacket(UINT16 eventCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3) {
        super(Type.EVENT);

        Validators.validateNonNull("eventCode", eventCode);
        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("p1", p1);
        Validators.validateNonNull("p2", p2);
        Validators.validateNonNull("p3", p3);

        this.eventCode = eventCode;
        this.transactionID = transactionID;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        super.payload = ByteUtils.join(
                eventCode.bytes(),
                transactionID.bytes(),
                p1.bytes(), p2.bytes(), p3.bytes()
        );
    }

    public UINT16 getEventCode() {
        return eventCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventPacket that = (EventPacket) o;

        if (!eventCode.equals(that.eventCode)) return false;
        if (!p1.equals(that.p1)) return false;
        if (!p2.equals(that.p2)) return false;
        if (!p3.equals(that.p3)) return false;
        if (!transactionID.equals(that.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventCode.hashCode();
        result = 31 * result + transactionID.hashCode();
        result = 31 * result + p1.hashCode();
        result = 31 * result + p2.hashCode();
        result = 31 * result + p3.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EventPacket{" +
                "eventCode=" + eventCode +
                ", transactionID=" + transactionID +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                '}';
    }

    public static EventPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.EVENT, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkLength(SIZE, buffer.remaining());

        // Get Event Code
        byte[] eventCodeBytes = new byte[UINT16.SIZE];
        buffer.get(eventCodeBytes);
        UINT16 eventCode = new UINT16(eventCodeBytes);

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

        return new EventPacket(eventCode, transactionID, p1, p2, p3);
    }
}
