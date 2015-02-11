package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpOutputStream;
import com.theta360.ptp.type.UINT32;

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
