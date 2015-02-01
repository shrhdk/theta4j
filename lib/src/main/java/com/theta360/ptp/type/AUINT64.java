package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AUINT64 {
    private AUINT64() {
        throw new AssertionError();
    }

    public static List<UINT64> valueOf(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        try (InputStream is = new ByteArrayInputStream(bytes)) {
            return read(is);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static List<UINT64> read(InputStream is) throws IOException {
        Validators.validateNonNull("is", is);

        long length = UINT32.read(is).longValue();

        List<UINT64> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(UINT64.read(is));
        }

        return list;
    }
}
