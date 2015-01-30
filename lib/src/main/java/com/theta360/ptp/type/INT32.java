package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class INT32 extends Number implements Comparable<INT32> {
    private final byte[] bytes;
    private final int intValue;

    // Utility Field

    public static final int SIZE_IN_BYTES = 4;

    public static final INT32 ZERO = new INT32(0);
    public static final INT32 MIN_VALUE = new INT32(Integer.MIN_VALUE);
    public static final INT32 MAX_VALUE = new INT32(Integer.MAX_VALUE);

    // Constructor

    public INT32(int intValue) {
        this.intValue = intValue;

        ByteBuffer bb = ByteBuffer.allocate(SIZE_IN_BYTES);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(intValue);

        this.bytes = bb.array();
    }

    public INT32(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.bytes = bytes.clone();

        // bytes -> int
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        this.intValue = bb.getInt();
    }

    // Static Factory Method

    public static INT32 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new INT32(bytes);
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
    public int compareTo(INT32 o) {
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

        INT32 rhs = (INT32) o;

        return intValue == rhs.intValue;

    }

    @Override
    public int hashCode() {
        return intValue;
    }

    @Override
    public String toString() {
        return String.format("0x%04x", intValue);
    }
}
