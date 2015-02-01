package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AINT8 {
    private AINT8() {
        throw new AssertionError();
    }

    public static List<INT8> read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        long length = UINT32.read(is).longValue();

        List<INT8> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(INT8.read(is));
        }

        return list;
    }
}
