package com.theta360.ptp.type;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * 16 bit unsigned integer value defined in PTP
 */
public final class UINT16 implements Comparable<UINT16> {
    /**
     * Size of type in bytes.
     */
    public static final int SIZE = 2;

    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 65535;

    private final byte[] bytes;
    private final int intValue;

    public UINT16(int intValue) {
        if (intValue < MIN_VALUE) {
            throw new IllegalArgumentException();
        }

        if (MAX_VALUE < intValue) {
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

    public UINT16(int b1, int b0) {
        // byte[]
        this.bytes = new byte[]{(byte) b1, (byte) b0};

        // long
        byte[] base = new byte[]{0x00, 0x00, (byte) b0, (byte) b1};
        ByteBuffer byteBuffer = ByteBuffer.wrap(base);
        this.intValue = byteBuffer.getInt();
    }

    public UINT16(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException();
        }

        // byte[]
        this.bytes = bytes.clone();

        // long
        byte[] base = new byte[]{0x00, 0x00, bytes[1], bytes[0]};
        ByteBuffer byteBuffer = ByteBuffer.wrap(base);
        this.intValue = byteBuffer.getInt();
    }

    public int intValue() {
        return intValue;
    }

    public byte[] bytes() {
        return bytes.clone();
    }

    @Override
    public int compareTo(UINT16 o) {
        return Integer.compare(intValue, o.intValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UINT16 uint16 = (UINT16) o;

        if (intValue != uint16.intValue) return false;

        return true;
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

    public static UINT16 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new UINT16(bytes);
    }
}
