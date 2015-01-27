package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 16 bit unsigned integer value defined in PTP
 */
public final class UINT16 extends Number implements Comparable<UINT16> {
    private static final int MIN_INTEGER_VALUE = 0;
    private static final int MAX_INTEGER_VALUE = 65535;

    private final byte[] bytes;
    private final int intValue;

    // Utility Field

    /**
     * Size of type in bytes.
     */
    public static final int SIZE_IN_BYTES = 2;

    public static final UINT16 ZERO = new UINT16(0);
    public static final UINT16 MIN_VALUE = new UINT16(MIN_INTEGER_VALUE);
    public static final UINT16 MAX_VALUE = new UINT16(MAX_INTEGER_VALUE);

    // Constructor

    public UINT16(int intValue) {
        if (intValue < MIN_INTEGER_VALUE) {
            throw new IllegalArgumentException();
        }

        if (MAX_INTEGER_VALUE < intValue) {
            throw new IllegalArgumentException();
        }

        // byte[]
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x00, 0x00, 0x00, 0x00});
        buffer.putInt(intValue);
        this.bytes = new byte[]{
                buffer.get(3),
                buffer.get(2),
        };

        // long
        this.intValue = intValue;
    }

    public UINT16(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.bytes = bytes.clone();

        // bytes -> int
        byte[] base = new byte[]{0x00, 0x00, bytes[1], bytes[0]};
        ByteBuffer byteBuffer = ByteBuffer.wrap(base);
        this.intValue = byteBuffer.getInt();
    }

    // Static Factory Method

    public static UINT16 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new UINT16(bytes);
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

    // Basic Method

    @Override
    public int compareTo(UINT16 o) {
        return Integer.compare(intValue, o.intValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UINT16 uint16 = (UINT16) o;

        return intValue == uint16.intValue;

    }

    @Override
    public int hashCode() {
        return intValue;
    }

    /**
     * String notated in hexadecimal big endian
     */
    @Override
    public String toString() {
        return String.format("0x%02x%02x", bytes[1], bytes[0]);
    }
}
