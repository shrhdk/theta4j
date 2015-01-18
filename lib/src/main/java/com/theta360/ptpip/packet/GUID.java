package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

final class GUID {
    public static final int SIZE = 16;

    private GUID() {
    }

    public static byte[] toBytes(UUID guid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(guid.getMostSignificantBits());
        buffer.putLong(guid.getLeastSignificantBits());
        return buffer.array();
    }

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        return new UUID(buffer.getLong(), buffer.getLong());
    }

    public static UUID read(PtpInputStream pis) throws IOException {
        byte[] bytes = new byte[SIZE];

        if (pis.read(bytes) == -1) {
            throw new IOException();
        }

        return toUUID(bytes);
    }
}
