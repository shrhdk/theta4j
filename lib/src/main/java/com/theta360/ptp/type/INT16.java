package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class INT16 extends Number implements Comparable<INT16> {

    private final byte[] bytes;
    private final short shortValue;

    // Utility Field

    public static final int SIZE_IN_BYTES = 2;

    public static final INT16 ZERO = new INT16((short) 0);
    public static final INT16 MIN_VALUE = new INT16(Short.MIN_VALUE);
    public static final INT16 MAX_VALUE = new INT16(Short.MAX_VALUE);

    // Constructor

    public INT16(short shortValue) {
        this.shortValue = shortValue;

        ByteBuffer bb = ByteBuffer.allocate(SIZE_IN_BYTES);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort(shortValue);

        this.bytes = bb.array();
    }

    public INT16(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.bytes = bytes.clone();

        // bytes -> short
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        this.shortValue = bb.getShort();
    }

    // Static Factory Method

    public static INT16 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new INT16(bytes);
    }

    // Number

    @Override
    public int intValue() {
        return shortValue;
    }

    @Override
    public long longValue() {
        return shortValue;
    }

    @Override
    public float floatValue() {
        return shortValue;
    }

    @Override
    public double doubleValue() {
        return shortValue;
    }

    // Comparable

    @Override
    public int compareTo(INT16 o) {
        return Short.compare(shortValue, o.shortValue);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        INT16 int16 = (INT16) o;

        return shortValue == int16.shortValue;

    }

    @Override
    public int hashCode() {
        return (int) shortValue;
    }

    /**
     * String notated in hexadecimal big endian
     */
    @Override
    public String toString() {
        return String.format("0x%02x%02x", bytes[1], bytes[0]);
    }
}
