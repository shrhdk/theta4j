package com.theta360.util;

public final class Validators {
    private Validators() {
    }

    public static void validateNonNull(String name, Object value) {
        if (value == null) {
            throw new NullPointerException(String.format("%s must not be null.", name));
        }
    }
}
