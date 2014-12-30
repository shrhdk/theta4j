package com.theta360.ptp.data;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public final class GUID {
    public static final int SIZE = 16;

    private final byte[] bytes;

    public GUID(int b15, int b14, int b13, int b12, int b11, int b10, int b9, int b8,
                int b7, int b6, int b5, int b4, int b3, int b2, int b1, int b0) {
        this.bytes = new byte[]{
                (byte) b15, (byte) b14, (byte) b13, (byte) b12, (byte) b11, (byte) b10, (byte) b9, (byte) b8,
                (byte) b7, (byte) b6, (byte) b5, (byte) b4, (byte) b3, (byte) b2, (byte) b1, (byte) b0
        };
    }

    public GUID(byte[] bytes) {
        if (bytes.length != 16) {
            throw new IllegalArgumentException("The GUID must consists of 16 Bytes.");
        }

        this.bytes = bytes.clone();
    }

    public GUID(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        this.bytes = buffer.array();
    }

    public byte[] bytes() {
        return bytes.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GUID guid = (GUID) o;

        if (!Arrays.equals(bytes, guid.bytes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return "GUID{" + Arrays.toString(bytes) + "}";
    }
}
