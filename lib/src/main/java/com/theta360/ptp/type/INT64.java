package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class INT64 extends Number implements Comparable<INT64> {
    private final byte[] bytes;
    private final long longValue;

    // Utility Field

    public static final int SIZE_IN_BYTES = 8;

    public static final INT64 ZERO = new INT64(0);
    public static final INT64 MIN_VALUE = new INT64(Long.MIN_VALUE);
    public static final INT64 MAX_VALUE = new INT64(Long.MAX_VALUE);

    // Constructor

    public INT64(long longValue) {
        this.longValue = longValue;

        ByteBuffer bb = ByteBuffer.allocate(SIZE_IN_BYTES);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putLong(longValue);

        this.bytes = bb.array();
    }

    public INT64(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.bytes = bytes.clone();

        // bytes -> long
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        this.longValue = bb.getLong();
    }

    // Static Factory Method

    public static INT64 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new INT64(bytes);
    }

    // Number

    @Override
    public int intValue() {
        return (int) longValue;
    }

    @Override
    public long longValue() {
        return longValue;
    }

    @Override
    public float floatValue() {
        return longValue;
    }

    @Override
    public double doubleValue() {
        return longValue;
    }

    // Comparable

    @Override
    public int compareTo(INT64 o) {
        return Long.compare(longValue, o.longValue);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        INT64 int64 = (INT64) o;

        return longValue == int64.longValue;

    }

    @Override
    public int hashCode() {
        return (int) (longValue ^ (longValue >>> 32));
    }

    @Override
    public String toString() {
        return String.format("0x%08x", longValue);
    }
}
