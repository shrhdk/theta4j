package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.io.IOException;
import java.util.UUID;

/**
 * InitCommandRequest Packet defined in PTP-IP
 */
public final class InitCommandRequestPacket extends PtpIpPacket {
    private static final int MIN_SIZE = GUID.SIZE + STR.MIN_SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES;

    private final UUID guid;
    private final String name;
    private final UINT32 protocolVersion;

    // Constructor

    public InitCommandRequestPacket(UUID guid, String name, UINT32 protocolVersion) {
        super(Type.INIT_COMMAND_REQUEST);

        Validators.validateNonNull("guid", guid);
        Validators.validateNonNull("name", name);
        Validators.validateNonNull("protocolVersion", protocolVersion);

        this.guid = guid;
        this.name = name;
        this.protocolVersion = protocolVersion;
        super.payload = ByteUtils.join(
                GUID.toBytes(guid),
                PtpIpString.toBytes(name),
                protocolVersion.bytes()
        );
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

    // Static Factory Method

    public static InitCommandRequestPacket read(PtpInputStream pis) throws IOException {
        long length = pis.readUINT32().longValue();
        long payloadLength = length - UINT32.SIZE_IN_BYTES - UINT32.SIZE_IN_BYTES;
        PtpIpPacket.Type type = PtpIpPacket.Type.read(pis);

        PacketUtils.assertType(type, Type.INIT_COMMAND_REQUEST);
        PacketUtils.checkMinLength((int) payloadLength, MIN_SIZE);

        UUID guid = GUID.read(pis);
        String name = PtpIpString.read(pis);
        UINT32 protocolVersion = pis.readUINT32();

        return new InitCommandRequestPacket(guid, name, protocolVersion);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitCommandRequestPacket that = (InitCommandRequestPacket) o;

        if (!guid.equals(that.guid)) return false;
        if (!name.equals(that.name)) return false;
        if (!protocolVersion.equals(that.protocolVersion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + protocolVersion.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InitCommandRequestPacket{" +
                "guid=" + guid +
                ", name='" + name + '\'' +
                ", protocolVersion=" + protocolVersion +
                '}';
    }
}
