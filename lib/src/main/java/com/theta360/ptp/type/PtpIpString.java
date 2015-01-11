package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class PtpIpString {
    public static final int MIN_SIZE = UINT16.SIZE;

    private static Charset CHARSET = Charset.forName("UTF-16LE");

    private PtpIpString() {
    }

    public static byte[] toBytes(String str) {
        Validators.validateNonNull("str", str);

        return (str + "\u0000").getBytes(CHARSET);
    }

    public static String read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (; ; ) {
            int b0 = is.read();
            if (b0 == -1) {
                throw new RuntimeException();
            }

            int b1 = is.read();
            if (b1 == -1) {
                throw new RuntimeException();
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
