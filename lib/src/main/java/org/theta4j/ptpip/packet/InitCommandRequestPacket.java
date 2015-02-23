package org.theta4j.ptpip.packet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.STR;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;
import org.theta4j.util.Validators;

import java.io.IOException;
import java.util.UUID;

/**
 * InitCommandRequest Packet defined in PTP-IP
 */
public final class InitCommandRequestPacket extends PtpIpPacket {
    private static final int MIN_SIZE_IN_BYTES = GUID.SIZE_IN_BYTES + STR.MIN_SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES;

    private final UUID guid;
    private final String name;
    private final UINT32 protocolVersion;

    private final byte[] payload;

    // Constructor

    public InitCommandRequestPacket(UUID guid, String name, UINT32 protocolVersion) {
        Validators.validateNonNull("guid", guid);
        Validators.validateNonNull("name", name);
        Validators.validateNonNull("protocolVersion", protocolVersion);

        this.guid = guid;
        this.name = name;
        this.protocolVersion = protocolVersion;
        this.payload = ArrayUtils.join(
                GUID.toBytes(guid),
                PtpIpString.toBytes(name),
                protocolVersion.bytes()
        );
    }

    // Static Factory Method

    public static InitCommandRequestPacket read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        // Read Header
        long length = pis.readUINT32().longValue();
        long payloadLength = length - HEADER_SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        // Validate Header
        PtpIpPacketUtils.assertType(type, Type.INIT_COMMAND_REQUEST);
        PtpIpPacketUtils.checkMinLength((int) payloadLength, MIN_SIZE_IN_BYTES);

        // Read Body
        UUID guid = GUID.read(pis);
        String name = PtpIpString.read(pis);
        UINT32 protocolVersion = pis.readUINT32();

        return new InitCommandRequestPacket(guid, name, protocolVersion);
    }

    // PtpIpPacket

    @Override
    Type getType() {
        return Type.INIT_COMMAND_REQUEST;
    }

    @Override
    byte[] getPayload() {
        return payload;
    }

    // Getter

    public UUID getGUID() {
        return guid;
    }

    public String getName() {
        return name;
    }

    public UINT32 getProtocolVersion() {
        return protocolVersion;
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

        InitCommandRequestPacket rhs = (InitCommandRequestPacket) o;

        return new EqualsBuilder()
                .append(guid, rhs.guid)
                .append(name, rhs.name)
                .append(protocolVersion, rhs.protocolVersion)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(guid)
                .append(name)
                .append(protocolVersion)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("guid", guid)
                .append("name", name)
                .append("protocolVersion", protocolVersion)
                .toString();
    }
}
