/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ArrayUtils {
    private ArrayUtils() {
        throw new AssertionError();
    }

    public static byte[] join(byte[]... byteArrays) {
        // Calculate Total Length
        int length = 0;
        for (byte[] bytes : byteArrays) {
            length += bytes.length;
        }

        // Join Byte Arrays
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream(length)) {
            for (byte[] bytes : byteArrays) {
                baos.write(bytes);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
