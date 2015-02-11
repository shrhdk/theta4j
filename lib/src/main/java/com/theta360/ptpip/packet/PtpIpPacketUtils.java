package com.theta360.ptpip.packet;

import com.theta360.ptp.type.UINT32;

import java.io.EOFException;
import java.io.IOException;

class PtpIpPacketUtils {
    private PtpIpPacketUtils() {
        throw new AssertionError();
    }

    public static void assertType(PtpIpPacket.Type actual, PtpIpPacket.Type expected) throws IOException {
        if (actual != expected) {
            throw new IOException(String.format("Unexpected packet type: Actual=%s, Expected=%s.", actual, expected));
        }
    }

    public static void assertType(PtpIpPacket.Type actual, UINT32 expected, PtpIpPacket.Type label) throws IOException {
        if (!actual.value().equals(expected)) {
            throw new IOException(String.format("Unexpected packet type: Actual=%s, Expected=%s.", actual, label));
        }
    }

    public static void checkLength(int actual, int expected) throws IOException {
        if (expected != actual) {
            throw new IOException(String.format("Unexpected packet length: Actual=%d, Expected=%d.", actual, expected));
        }
    }

    public static void checkMinLength(int actual, int min) throws EOFException {
        if (actual < min) {
            throw new EOFException(String.format("Too short packet length: actual=%d, min=%d.", actual, min));
        }
    }
}
