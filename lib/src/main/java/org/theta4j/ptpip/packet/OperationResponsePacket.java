package org.theta4j.ptpip.packet;

import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;
import org.theta4j.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * OperationResponse Packet defined in PTP-IP
 */
public final class OperationResponsePacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = UINT16.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES * 5;

    private final UINT16 responseCode;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3, p4, p5;

    private final byte[] payload;

    // Constructor

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

        this.payload = ArrayUtils.join(
                responseCode.bytes(),
                transactionID.bytes(),
                p1.bytes(), p2.bytes(), p3.bytes(), p4.bytes(), p5.bytes()
        );
    }

    // Static Factory Method

    public static OperationResponsePacket read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PtpIpPacketUtils.assertType(type, Type.OPERATION_RESPONSE);
        PtpIpPacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        // Read Body
        UINT16 responseCode = pis.readUINT16();
        UINT32 transactionID = pis.readUINT32();
        UINT32 p1 = pis.readUINT32();
        UINT32 p2 = pis.readUINT32();
        UINT32 p3 = pis.readUINT32();
        UINT32 p4 = pis.readUINT32();
        UINT32 p5 = pis.readUINT32();

        return new OperationResponsePacket(responseCode, transactionID, p1, p2, p3, p4, p5);
    }

    // PtpIpPacket

    @Override
    Type getType() {
        return Type.OPERATION_RESPONSE;
    }

    @Override
    byte[] getPayload() {
        return payload;
    }

    // Getter

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

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperationResponsePacket rhs = (OperationResponsePacket) o;

        return new EqualsBuilder()
                .append(responseCode, rhs.responseCode)
                .append(transactionID, rhs.transactionID)
                .append(p1, rhs.p1)
                .append(p2, rhs.p2)
                .append(p3, rhs.p3)
                .append(p4, rhs.p4)
                .append(p5, rhs.p5)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(responseCode)
                .append(transactionID)
                .append(p1)
                .append(p2)
                .append(p3)
                .append(p4)
                .append(p5)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("responseCode", responseCode)
                .append("transactionID", transactionID)
                .append("p1", p1)
                .append("p2", p2)
                .append("p3", p3)
                .append("p4", p4)
                .append("p5", p5)
                .toString();
    }
}
