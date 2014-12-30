package com.theta360.ptp.packet;

import com.theta360.util.Validators;

public final class ProbeRequestPacket extends PtpIpPacket {
    private static final int SIZE = 0;

    public ProbeRequestPacket() {
        super(Type.PROBE_REQUEST);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Type.PROBE_REQUEST.ordinal();
    }

    @Override
    public String toString() {
        return "ProbeRequestPacket{}";
    }

    public static ProbeRequestPacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.PROBE_REQUEST, packet.getType());
        PacketUtils.checkLength(SIZE, packet.getPayload().length);

        return new ProbeRequestPacket();
    }
}
