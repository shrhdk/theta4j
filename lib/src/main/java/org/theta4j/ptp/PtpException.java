/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.type.UINT16;

import java.io.IOException;

public class PtpException extends IOException implements Code<UINT16> {
    private final UINT16 responseCode;

    // Constructor

    public PtpException(UINT16 responseCode) {
        this.responseCode = responseCode;
    }

    public PtpException(UINT16 responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public PtpException(UINT16 responseCode, String message, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
    }

    public PtpException(UINT16 responseCode, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
    }

    // Code

    /**
     * Returns the PTP response code of this exception.
     */
    @Override
    public UINT16 value() {
        return responseCode;
    }
}
