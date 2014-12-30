package com.theta360.ptp.packet;

public class PacketException extends Exception {
    public PacketException() {
        super();
    }

    public PacketException(String message) {
        super(message);
    }

    public PacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public PacketException(Throwable cause) {
        super(cause);
    }

    protected PacketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
