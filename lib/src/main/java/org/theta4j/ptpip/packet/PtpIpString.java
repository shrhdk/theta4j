package org.theta4j.ptpip.packet;

import org.theta4j.ptp.type.UINT16;
import org.theta4j.util.Validators;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Converter for java.lang.String and PTP-IP String
 */
final class PtpIpString {
    /**
     * Minimum size of type in bytes.
     */
    public static final int MIN_SIZE = UINT16.SIZE_IN_BYTES;

    private static Charset CHARSET = Charset.forName("UTF-16LE");

    private PtpIpString() {
        throw new AssertionError();
    }

    public static byte[] toBytes(String str) {
        Validators.notNull("str", str);

        return (str + "\u0000").getBytes(CHARSET);
    }

    public static String read(InputStream is) throws IOException {
        Validators.notNull("is", is);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (; ; ) {
            int b0 = is.read();
            if (b0 == -1) {
                throw new EOFException();
            }

            int b1 = is.read();
            if (b1 == -1) {
                throw new EOFException();
            }

            // End with NULL?
            if (b0 == 0 && b1 == 0) {
                break;
            }

            baos.write(b0);
            baos.write(b1);
        }

        return new String(baos.toByteArray(), CHARSET);
    }
}
