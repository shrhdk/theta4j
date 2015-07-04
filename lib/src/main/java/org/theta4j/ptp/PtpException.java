package org.theta4j.ptp;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.type.UINT16;

import java.io.IOException;

public class PtpException extends IOException implements Code<UINT16> {
    private final UINT16 value;

    // Constructor

    public PtpException(UINT16 value) {
        this.value = value;
    }

    public PtpException(UINT16 value, String message) {
        super(message);
        this.value = value;
    }

    public PtpException(UINT16 value, String message, Throwable cause) {
        super(message, cause);
        this.value = value;
    }

    public PtpException(UINT16 value, Throwable cause) {
        super(cause);
        this.value = value;
    }

    // Code

    @Override
    public UINT16 value() {
        return value;
    }
}
