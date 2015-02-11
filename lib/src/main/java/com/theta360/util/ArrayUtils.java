package com.theta360.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static byte[] join(byte[]... byteArrays) {
        // Calculate Total Length
        int length = 0;
        for (byte[] bytes : byteArrays) {
            length += bytes.length;
        }

        // Join Byte Arrays
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(length)) {
            for (byte[] bytes : byteArrays) {
                baos.write(bytes);
            }

            return baos.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
