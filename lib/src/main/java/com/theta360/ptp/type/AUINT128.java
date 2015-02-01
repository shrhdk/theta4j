package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AUINT128 {
    private AUINT128() {
        throw new AssertionError();
    }

    public static List<UINT128> read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        long length = UINT32.read(is).longValue();

        List<UINT128> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(UINT128.read(is));
        }

        return list;
    }
}
