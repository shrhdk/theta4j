package org.theta4j;

import org.theta4j.ptp.PtpException;

public class ThetaException extends Exception {
    private long code;

    // Constructor

    ThetaException(PtpException e) {
        super(e.getMessage(), e);
        code = e.value().longValue();
    }

    public ThetaException(long code) {
        this.code = code;
    }

    public ThetaException(long code, String message) {
        super(message);
        this.code = code;
    }

    public ThetaException(long code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ThetaException(long code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    // Getter

    public long getCode() {
        return code;
    }
}
