package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;

import java.io.IOException;

class PacketUtils {
    private PacketUtils() {
    }

    public static void asseertType(PtpIpPacket.Type actual, PtpIpPacket.Type expected) throws IOException {
        if (actual != expected) {
            throw new IOException(String.format("Unexpected packet type: Actual=%s, Expected=%s.", actual, expected));
        }
    }

    public static void asseertType(PtpIpPacket.Type actual, UINT32 expected, PtpIpPacket.Type label) throws IOException {
        if (!actual.getCode().equals(expected)) {
            throw new IOException(String.format("Unexpected packet type: Actual=%s, Expected=%s.", actual, label));
        }
    }

    public static void checkLength(int actual, int expected) throws IOException {
        if (expected != actual) {
            throw new IOException(String.format("Unexpected packet length: Actual=%d, Expected=%d.", actual, expected));
        }
    }

    public static void checkMinLength(int actual, int min) throws IOException {
        if (actual < min) {
            throw new IOException(String.format("Too short packet length: actual=%d, min=%d.", actual, min));
        }
    }
}
