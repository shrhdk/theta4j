package com.theta360.ptp.packet;

import com.theta360.util.Validators;

public final class ProbeResponsePacket extends PtpIpPacket {
    private static final int SIZE = 0;

    public ProbeResponsePacket() {
        super(Type.PROBE_RESPONSE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Type.PROBE_RESPONSE.ordinal();
    }

    @Override
    public String toString() {
        return "ProbeResponsePacket{}";
    }

    public static ProbeResponsePacket valueOf(PtpIpPacket packet) throws PacketException {
        Validators.validateNonNull("packet", packet);
        PacketUtils.checkType(Type.PROBE_RESPONSE, packet.getType());
        PacketUtils.checkLength(SIZE, packet.getPayload().length);

        return new ProbeResponsePacket();
    }
}
