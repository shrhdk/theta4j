package com.theta360.ptpip.packet;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

final class GUID {
    public static final int SIZE_IN_BYTES = 16;

    private GUID() {
        throw new AssertionError();
    }

    public static byte[] toBytes(UUID guid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[SIZE_IN_BYTES]);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(guid.getMostSignificantBits());
        buffer.putLong(guid.getLeastSignificantBits());
        return buffer.array();
    }

    public static UUID read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new EOFException();
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        return new UUID(buffer.getLong(), buffer.getLong());
    }
}
