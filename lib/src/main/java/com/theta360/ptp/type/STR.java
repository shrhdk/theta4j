package com.theta360.ptp.type;

import com.theta360.util.ByteUtils;
import com.theta360.util.Validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Converter for java.lang.String and PTP String
 */
public final class STR {
    /**
     * Minimum size of type in bytes.
     */
    public static final int MIN_SIZE_IN_BYTES = 1;

    private static Charset CHARSET = Charset.forName("UTF-16LE");

    private STR() {
    }

    public static byte[] toBytes(String str) {
        Validators.validateNonNull("str", str);

        byte[] length = new byte[]{(byte) str.length()};
        return ByteUtils.join(length, str.getBytes(CHARSET));
    }

    public static String valueOf(byte[] bytes) throws IOException {
        Validators.validateNonNull("bytes", bytes);

        try (InputStream is = new ByteArrayInputStream(bytes)) {
            return read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        int numChars = is.read();
        if (numChars == -1) {
            throw new IOException();
        }

        if (numChars == 0) {
            return "";
        }

        int length = numChars * UINT16.SIZE_IN_BYTES;

        byte[] bytes = new byte[length];
        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new String(bytes, CHARSET);
    }
}
