package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;

public final class INT8 extends Number implements Comparable<INT8> {
    private final byte byteValue;

    // Utility Field

    public static final int SIZE_IN_BYTES = 1;

    public static final INT8 ZERO = new INT8((byte) 0);
    public static final INT8 MIN_VALUE = new INT8(Byte.MIN_VALUE);
    public static final INT8 MAX_VALUE = new INT8(Byte.MAX_VALUE);

    // Constructor

    public INT8(byte byteValue) {
        this.byteValue = byteValue;
    }

    public INT8(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.byteValue = bytes[0];
    }

    // Static Factory Method

    public static INT8 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new INT8(bytes);
    }

    // Number

    @Override
    public int intValue() {
        return byteValue;
    }

    @Override
    public long longValue() {
        return byteValue;
    }

    @Override
    public float floatValue() {
        return byteValue;
    }

    @Override
    public double doubleValue() {
        return byteValue;
    }

    // Comparable

    @Override
    public int compareTo(INT8 o) {
        return Byte.compare(byteValue, o.byteValue);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        INT8 int8 = (INT8) o;

        return byteValue == int8.byteValue;

    }

    @Override
    public int hashCode() {
        return (int) byteValue;
    }

    @Override
    public String toString() {
        return String.format("0x%02x", (byte) byteValue);
    }
}
