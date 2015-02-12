package org.theta4j.ptpip.packet;

import org.theta4j.ptp.io.PtpOutputStream;
import org.theta4j.ptp.type.UINT32;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class PtpIpPacketTestUtils {
    private PtpIpPacketTestUtils() {
        throw new AssertionError();
    }

    public static byte[] bytes(PtpIpPacket.Type type, byte[] payload) {
        UINT32 length = new UINT32(UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES + payload.length);

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PtpOutputStream pos = new PtpOutputStream(baos);
        ) {
            pos.write(length);
            pos.write(type.value());
            pos.write(payload);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
