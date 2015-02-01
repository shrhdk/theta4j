package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AINT16 {
    private AINT16() {
        throw new AssertionError();
    }

    public static List<INT16> read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        long length = UINT32.read(is).longValue();

        List<INT16> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(INT16.read(is));
        }

        return list;
    }
}
