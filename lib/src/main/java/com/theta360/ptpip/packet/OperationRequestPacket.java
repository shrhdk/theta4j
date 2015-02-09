package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * OperationRequest Packet defined in PTP-IP
 */
public final class OperationRequestPacket extends PtpIpPacket {
    private static final int SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES + UINT16.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES * 5;

    private final UINT32 dataPhaseInfo;
    private final UINT16 operationCode;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3, p4, p5;

    // Constructor

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID) {
        this(dataPhaseInfo, operationCode, transactionID, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1) {
        this(dataPhaseInfo, operationCode, transactionID, p1, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2) {
        this(dataPhaseInfo, operationCode, transactionID, p1, p2, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3) {
        this(dataPhaseInfo, operationCode, transactionID, p1, p2, p3, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) {
        this(dataPhaseInfo, operationCode, transactionID, p1, p2, p3, p4, new UINT32(0));
    }

    public OperationRequestPacket(UINT32 dataPhaseInfo, UINT16 operationCode, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) {
        super(Type.OPERATION_REQUEST);

        Validators.validateNonNull("dataPhaseInfo", dataPhaseInfo);
        Validators.validateNonNull("operationCode", operationCode);
        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("p1", p1);
        Validators.validateNonNull("p2", p2);
        Validators.validateNonNull("p3", p3);
        Validators.validateNonNull("p4", p4);
        Validators.validateNonNull("p5", p5);

        this.dataPhaseInfo = dataPhaseInfo;
        this.operationCode = operationCode;
        this.transactionID = transactionID;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;

        super.payload = ByteUtils.join(
                dataPhaseInfo.bytes(),
                operationCode.bytes(),
                transactionID.bytes(),
                p1.bytes(),
                p2.bytes(),
                p3.bytes(),
                p4.bytes(),
                p5.bytes()
        );
    }

    // Static Factory Method

    public static OperationRequestPacket read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PacketUtils.assertType(type, Type.OPERATION_REQUEST);
        PacketUtils.checkLength((int) payloadLength, SIZE_IN_BYTES);

        // Read Body
        UINT32 dataPhaseInfo = pis.readUINT32();
        UINT16 operationCode = pis.readUINT16();
        UINT32 transactionID = pis.readUINT32();
        UINT32 p1 = pis.readUINT32();
        UINT32 p2 = pis.readUINT32();
        UINT32 p3 = pis.readUINT32();
        UINT32 p4 = pis.readUINT32();
        UINT32 p5 = pis.readUINT32();

        return new OperationRequestPacket(dataPhaseInfo, operationCode, transactionID, p1, p2, p3, p4, p5);
    }

    // Getter

    public UINT32 getDataPhaseInfo() {
        return dataPhaseInfo;
    }

    public UINT16 getOperationCode() {
        return operationCode;
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

        OperationRequestPacket rhs = (OperationRequestPacket) o;

        return new EqualsBuilder()
                .append(dataPhaseInfo, rhs.dataPhaseInfo)
                .append(operationCode, rhs.operationCode)
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
                .append(dataPhaseInfo)
                .append(operationCode)
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
                .append("dataPhaseInfo", dataPhaseInfo)
                .append("operationCode", operationCode)
                .append("transactionID", transactionID)
                .append("p1", p1)
                .append("p2", p2)
                .append("p3", p3)
                .append("p4", p4)
                .append("p5", p5)
                .toString();
    }
}
