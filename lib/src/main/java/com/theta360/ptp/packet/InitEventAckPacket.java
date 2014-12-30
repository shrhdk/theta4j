package com.theta360.ptp.packet;

import com.theta360.util.Validators;

public final class InitEventAckPacket extends PtpIpPacket {
    private static final int SIZE = 0;

    public InitEventAckPacket() {
        super(Type.INIT_EVENT_ACK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Type.INIT_EVENT_ACK.ordinal();
    }

    @Override
    public String toString() {
        return "InitEventAckPacket{}";
    }

    public static InitEventAckPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.INIT_EVENT_ACK, packet.getType());
        PacketUtils.checkLength(SIZE, packet.getPayload().length);

        return new InitEventAckPacket();
    }
}
