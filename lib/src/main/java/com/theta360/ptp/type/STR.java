package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public final class STR {
    public static final int MIN_SIZE = UINT16.SIZE;

    private static Charset CHARSET = Charset.forName("UTF-16LE");

    private STR() {
    }

    public static byte[] toBytes(String str) {
        Validators.validateNonNull("str", str);

        return (str + "\u0000").getBytes(CHARSET);
    }

    public static String toString(byte[] bytes) throws IOException {
        Validators.validateNonNull("bytes", bytes);

        // Larger than min length?
        if (bytes.length < MIN_SIZE) {
            throw new IOException("String must be at least 2 bytes, but actual is 0 byte.");
        }

        // End with NULL?
        boolean endWithNull = bytes[bytes.length - 1] == 0x00 && bytes[bytes.length - 2] == 0x00;
        if (!endWithNull) {
            throw new IOException("String bytes must be end with NULL, but actual is not.");
        }

        // Decode without NUll at the end
        int offset = 0;
        int length = bytes.length - UINT16.SIZE;
        return new String(bytes, offset, length, CHARSET);
    }

    public static String read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        int numChars = is.read();
        int length = numChars * UINT16.SIZE;

        return read(is, length);
    }

    public static String read(InputStream is, int length) throws IOException {
        Validators.validateNonNull("is", is);

        byte[] bytes = new byte[length];
        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return STR.toString(bytes);
    }
}
