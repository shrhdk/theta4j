package com.theta360.ptp.packet;

class PacketUtils {
    private PacketUtils() {
    }

    public static void checkType(PtpIpPacket.Type expected, PtpIpPacket.Type actual) throws PacketException {
        if (actual != expected) {
            throw new PacketException(String.format("Unexpected packet type: Actual=%s, Expected=%s.", actual, expected));
        }
    }

    public static void checkLength(int expected, int actual) throws PacketException {
        if (expected != actual) {
            throw new PacketException(String.format("Unexpected packet length: Actual=%d, Expected=%d.", actual, expected));
        }
    }

    public static void checkMinLength(int min, int actual) throws PacketException {
        if (actual < min) {
            throw new PacketException(String.format("Too short packet length: actual=%d, min=%d.", actual, min));
        }
    }
}
