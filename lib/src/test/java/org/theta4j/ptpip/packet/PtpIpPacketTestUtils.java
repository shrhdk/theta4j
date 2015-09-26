/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.theta4j.util.Closer;
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

        Closer closer = new Closer();
        try {
            ByteArrayOutputStream baos = closer.push(new ByteArrayOutputStream());
            PtpOutputStream pos = closer.push(new PtpOutputStream(baos));
            pos.write(length);
            pos.write(type.value());
            pos.write(payload);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        } finally {
            closer.close();
        }
    }
}
