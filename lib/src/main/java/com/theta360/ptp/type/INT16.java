package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class INT16 {
    public static final int SIZE = 2;

    private INT16() {
    }

    public static byte[] toBytes;

    public static short toShort(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getShort();
    }

    public static short read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return toShort(bytes);
    }
}
