package com.theta360.ptp;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.type.UINT16;

public class PtpException extends Exception implements Code<UINT16> {
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
