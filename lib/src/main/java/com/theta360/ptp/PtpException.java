package com.theta360.ptp;

public class PtpException extends Exception {
    private final int code;

    // Constructor

    public PtpException(int code) {
        this.code = code;
    }

    public PtpException(int code, String message) {
        super(message);
        this.code = code;
    }

    public PtpException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public PtpException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public PtpException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    // Getter
    
    public int getCode() {
        return code;
    }
}
