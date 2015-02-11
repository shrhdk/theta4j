package com.theta360.ptp.type;

import com.theta360.util.ArrayUtils;
import com.theta360.util.Validators;

import java.io.EOFException;
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
        throw new AssertionError();
    }

    public static byte[] toBytes(String str) {
        Validators.validateNonNull("str", str);

        byte[] length = new byte[]{(byte) str.length()};
        return ArrayUtils.join(length, str.getBytes(CHARSET));
    }

    public static String read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        int numChars = is.read();

        if (numChars == -1) {
            throw new EOFException("length of InputStream is 0.");
        }

        if (numChars == 0) {
            return "";
        }

        int numBytes = numChars * UINT16.SIZE_IN_BYTES;

        byte[] bytes = new byte[numBytes];
        int numReadBytes = is.read(bytes);
        if (numReadBytes != numBytes) {
            String message = String.format("NumChars is %d (= %d bytes), but actual data is %d bytes.", numChars, numBytes, numReadBytes);
            throw new EOFException(message);
        }

        return new String(bytes, CHARSET);
    }
}
