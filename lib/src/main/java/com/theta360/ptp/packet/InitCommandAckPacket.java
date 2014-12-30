package com.theta360.ptp.packet;

import com.theta360.ptp.data.GUID;
import com.theta360.ptp.type.ConvertException;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class InitCommandAckPacket extends PtpIpPacket {
    private static final int MIN_SIZE = UINT32.SIZE + GUID.SIZE + STR.MIN_SIZE + UINT32.SIZE;

    private final UINT32 connectionNumber;
    private final GUID guid;
    private final String name;
    private final UINT32 protocolVersion;

    public InitCommandAckPacket(UINT32 connectionNumber, GUID guid, String name, UINT32 protocolVersion) {
        super(Type.INIT_COMMAND_ACK);

        Validators.validateNonNull("connectionNumber", connectionNumber);
        Validators.validateNonNull("guid", guid);
        Validators.validateNonNull("name", name);
        Validators.validateNonNull("protocolVersion", protocolVersion);

        byte[] nameBytes = STR.toBytes(name);

        this.connectionNumber = connectionNumber;
        this.guid = guid;
        this.name = name;
        this.protocolVersion = protocolVersion;
        super.payload = ByteUtils.join(connectionNumber.bytes(), guid.bytes(), nameBytes, protocolVersion.bytes());
    }

    public UINT32 getConnectionNumber() {
        return connectionNumber;
    }

    public GUID getGUID() {
        return guid;
    }

    public String getName() {
        return name;
    }

    public UINT32 getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitCommandAckPacket that = (InitCommandAckPacket) o;

        if (!connectionNumber.equals(that.connectionNumber)) return false;
        if (!guid.equals(that.guid)) return false;
        if (!name.equals(that.name)) return false;
        if (!protocolVersion.equals(that.protocolVersion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = connectionNumber.hashCode();
        result = 31 * result + guid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + protocolVersion.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InitCommandAckPacket{" +
                "connectionNumber=" + connectionNumber +
                ", guid=" + guid +
                ", name='" + name + '\'' +
                ", protocolVersion=" + protocolVersion +
                '}';
    }

    public static InitCommandAckPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.INIT_COMMAND_ACK, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkMinLength(MIN_SIZE, buffer.remaining());

        // Get Connection Number
        byte[] connectionNumberBytes = new byte[UINT32.SIZE];
        buffer.get(connectionNumberBytes);
        UINT32 connectionNumber = new UINT32(connectionNumberBytes);

        // Get GUID
        byte[] guidBytes = new byte[GUID.SIZE];
        buffer.get(guidBytes);
        GUID guid = new GUID(guidBytes);

        // Get Name
        byte[] nameBytes = new byte[buffer.remaining() - UINT32.SIZE];
        buffer.get(nameBytes);

        // Get Protocol Version
        byte[] protocolVersionBytes = new byte[UINT32.SIZE];
        buffer.get(protocolVersionBytes);
        UINT32 protocolVersion = new UINT32(protocolVersionBytes);

        // Convert bytes to String
        String name;
        try {
            name = STR.toString(nameBytes);
        } catch (ConvertException e) {
            throw new PacketException(e.getMessage(), e);
        }

        return new InitCommandAckPacket(connectionNumber, guid, name, protocolVersion);
    }
}
