package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AUINT8 {
    private AUINT8() {
        throw new AssertionError();
    }

    public static List<UINT8> valueOf(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        try (InputStream is = new ByteArrayInputStream(bytes)) {
            return read(is);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
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
