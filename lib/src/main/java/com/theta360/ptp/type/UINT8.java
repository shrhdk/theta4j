package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;

public final class UINT8 extends Number implements Comparable<UINT8> {
    private static final int MIN_INTEGER_VALUE = 0;
    private static final int MAX_INTEGER_VALUE = 255;

    private final byte[] bytes;
    private final int intValue;

    // Utility Field

    public static final int SIZE_IN_BYTES = 1;

    public static final UINT8 ZERO = new UINT8(0);
    public static final UINT8 MIN_VALUE = new UINT8(MIN_INTEGER_VALUE);
    public static final UINT8 MAX_VALUE = new UINT8(MAX_INTEGER_VALUE);

    // Constructor

    public UINT8(int intValue) {
        if (intValue < MIN_INTEGER_VALUE || MAX_INTEGER_VALUE < intValue) {
            throw new IllegalArgumentException();
        }

        this.bytes = new byte[]{(byte) intValue};
        this.intValue = intValue;
    }

    public UINT8(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.bytes = bytes.clone();

        // bytes -> short
        this.intValue = bytes[0] & 0xFF;
    }

    // Static Factory Method

    public static UINT8 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new UINT8(bytes);
    }

    // Getter

    public byte[] bytes() {
        return bytes.clone();
    }

    // Number

    @Override
    public int intValue() {
        return intValue;
    }

    @Override
    public long longValue() {
        return intValue;
    }

    @Override
    public float floatValue() {
        return intValue;
    }

    @Override
    public double doubleValue() {
        return intValue;
    }

    // Comparable

    @Override
    public int compareTo(UINT8 o) {
        return Integer.compare(intValue, o.intValue);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UINT8 rhs = (UINT8) o;

        return intValue == rhs.intValue;

    }

    @Override
    public int hashCode() {
        return intValue;
    }

    @Override
    public String toString() {
        return String.format("0x%02x", (byte) intValue);
    }
}
