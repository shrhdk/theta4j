package org.theta4j.ptp.type;

import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AUINT64 {
    private AUINT64() {
        throw new AssertionError();
    }

    public static List<UINT64> read(InputStream is) throws IOException {
        Validators.notNull("is", is);

        long length = UINT32.read(is).longValue();

        List<UINT64> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(UINT64.read(is));
        }

        return list;
    }
}
