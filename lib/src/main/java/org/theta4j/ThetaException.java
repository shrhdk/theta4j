package org.theta4j;

import org.theta4j.ptp.PtpException;
import org.theta4j.ptp.code.Code;

public class ThetaException extends Exception implements Code<Long> {
    private long value;

    // Constructor

    ThetaException(PtpException e) {
        super(e.getMessage(), e);
        value = e.value().longValue();
    }

    public ThetaException(long value) {
        this.value = value;
    }

    public ThetaException(long value, String message) {
        super(message);
        this.value = value;
    }

    public ThetaException(long value, String message, Throwable cause) {
        super(message, cause);
        this.value = value;
    }

    public ThetaException(long value, Throwable cause) {
        super(cause);
        this.value = value;
    }

    // Code

    @Override
    public Long value() {
        return value;
    }
}
