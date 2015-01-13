package com.theta360.ptp.type;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.util.Validators;

import java.io.IOException;
import java.util.List;

public class AUINT32 {
    private AUINT32() {
    }

    public static List<UINT32> valueOf(byte[] bytes) throws IOException {
        Validators.validateNonNull("bytes", bytes);

        try (PtpInputStream pis = new PtpInputStream(bytes)) {
            return pis.readAUINT32();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
