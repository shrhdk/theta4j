package com.theta360.util;

public final class ByteUtils {
    private ByteUtils() {
    }

    public static byte[] join(byte[]... byteArrays) {
        int length = 0;
        for (byte[] bytes : byteArrays) {
            length += bytes.length;
        }

        byte[] joined = new byte[length];

        // join
        int pos = 0;
        for (byte[] src : byteArrays) {
            if (src == null) {
                throw new NullPointerException();
            }

            System.arraycopy(src, 0, joined, pos, src.length);
            pos += src.length;
        }

        return joined;
    }
}
