package com.theta360.util;

public final class Validators {
    private Validators() {
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
}
