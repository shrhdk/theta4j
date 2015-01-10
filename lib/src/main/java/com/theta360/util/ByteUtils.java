package com.theta360.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ByteUtils {
    private ByteUtils() {
    }

    public static byte[] join(byte[]... byteArrays) {
        // Calculate Total Length
        int length = 0;
        for (byte[] bytes : byteArrays) {
            length += bytes.length;
        }

        // Join Byte Arrays
        ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
        for (byte[] bytes : byteArrays) {
            try {
                baos.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return baos.toByteArray();
    }
}
