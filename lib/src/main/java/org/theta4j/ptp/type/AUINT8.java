package org.theta4j.ptp.type;

import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AUINT8 {
    private AUINT8() {
        throw new AssertionError();
    }

    public static List<UINT8> read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        long length = UINT32.read(is).longValue();

        List<UINT8> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(UINT8.read(is));
        }

        return list;
    }
}
