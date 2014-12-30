package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.nio.ByteBuffer;

public final class InitFailPacket extends PtpIpPacket {
    private static final int SIZE = UINT32.SIZE;

    private final UINT32 reason;

    public InitFailPacket(UINT32 reason) {
        super(Type.INIT_FAIL);

        Validators.validateNonNull("reason", reason);

        this.reason = reason;
        super.payload = reason.bytes();
    }

    public UINT32 getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitFailPacket that = (InitFailPacket) o;

        if (!reason.equals(that.reason)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return reason.hashCode();
    }

    @Override
    public String toString() {
        return "InitFailPacket{" +
                "reason=" + reason +
                '}';
    }

    public static InitFailPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.INIT_FAIL, packet.getType());

        ByteBuffer buffer = ByteBuffer.wrap(packet.getPayload());
        PacketUtils.checkMinLength(SIZE, buffer.remaining());

        // Get Error Code
        byte[] reasonBytes = new byte[UINT32.SIZE];
        buffer.get(reasonBytes);
        UINT32 reason = new UINT32(reasonBytes);

        return new InitFailPacket(reason);
    }
}
