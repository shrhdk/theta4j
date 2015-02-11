package com.theta360.util;

public final class Validators {
    private Validators() {
        throw new AssertionError();
    }

    public static void validateNonNull(String name, Object value) {
        if (value == null) {
            throw new NullPointerException(String.format("%s must not be null.", name));
        }
    }

    public static void validateLength(String name, byte[] value, int length) {
        if (value.length != length) {
            String message = String.format("%s must consists of %d Bytes. But actual is %d.", name, length, value.length);
            throw new IllegalArgumentException(message);
        }
    }

    public static void validatePortNumber(int port) {
        if (port < 0 || 65535 < port) {
            String message = String.format("TCP or UDP port number must be in 0-65535, but was %d.", port);
            throw new IllegalArgumentException(message);
        }
    }
}
