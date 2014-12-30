package com.theta360.ptp.type;

import java.nio.ByteBuffer;

public final class UINT32 implements Comparable<UINT32> {
    public static final int SIZE = 4;
    public static final long MIN_VALUE = 0L;
    public static final long MAX_VALUE = 4294967295L;

    private final byte[] bytes;
    private final long longValue;

    public UINT32(long longValue) {
        if (longValue < MIN_VALUE) {
            throw new IllegalArgumentException();
        }

        if (MAX_VALUE < longValue) {
            throw new IllegalArgumentException();
        }

        // byte[]
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        buffer.putLong(longValue);
        this.bytes = new byte[]{
                buffer.get(7),
                buffer.get(6),
                buffer.get(5),
                buffer.get(4)
        };

        // long
        this.longValue = longValue;
    }

    public UINT32(int b3, int b2, int b1, int b0) {
        // byte[]
        this.bytes = new byte[]{(byte) b3, (byte) b2, (byte) b1, (byte) b0};

        // long
        byte[] base = new byte[]{0x00, 0x00, 0x00, 0x00, (byte) b0, (byte) b1, (byte) b2, (byte) b3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(base);
        this.longValue = byteBuffer.getLong();
    }

    public UINT32(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException();
        }

        // byte[]
        this.bytes = bytes.clone();

        // long
        byte[] base = new byte[]{0x00, 0x00, 0x00, 0x00, bytes[3], bytes[2], bytes[1], bytes[0]};
        ByteBuffer byteBuffer = ByteBuffer.wrap(base);
        this.longValue = byteBuffer.getLong();
    }

    public long longValue() {
        return longValue;
    }

    public byte[] bytes() {
        return bytes.clone();
    }

    @Override
    public int compareTo(UINT32 o) {
        return Long.compare(longValue, o.longValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UINT32 uint32 = (UINT32) o;

        if (longValue != uint32.longValue) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (longValue ^ (longValue >>> 32));
    }

    /**
     * String notated in hexadecimal big endian
     */
    @Override
    public String toString() {
        return String.format("0x%02x%02x%02x%02x", bytes[3], bytes[2], bytes[1], bytes[0]);
    }
}
