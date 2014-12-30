package com.theta360.ptp.type;

import java.math.BigInteger;
import java.util.Arrays;

public final class UINT64 implements Comparable<UINT64> {
    public static final int SIZE = 8;

    public static final UINT64 ZERO = new UINT64(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00);

    private final BigInteger bigInteger;
    private final byte[] bytes;

    public UINT64(int b0, int b1, int b2, int b3, int b4, int b5, int b6, int b7) {
        this.bytes = new byte[]{
                (byte) b0, (byte) b1, (byte) b2, (byte) b3, (byte) b4, (byte) b5, (byte) b6, (byte) b7
        };

        // UINT64 constructor arguments are little endian and are not two's complement.
        // BigInteger constructor needs big endian two's complement value.
        // So it need to reverse order of arguments and add 0x00 to top.
        this.bigInteger = new BigInteger(new byte[]{
                0x00, (byte) b7, (byte) b6, (byte) b5, (byte) b4, (byte) b3, (byte) b2, (byte) b1, (byte) b0
        });
    }

    public UINT64(byte[] bytes) {
        if (bytes.length != 8) {
            throw new IllegalArgumentException();
        }

        this.bytes = bytes.clone();

        // UINT64 constructor arguments are little endian and are not two's complement.
        // BigInteger constructor needs big endian two's complement value.
        // So it need to reverse order of arguments and add 0x00 to top.
        this.bigInteger = new BigInteger(new byte[]{
                0x00, bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]
        });
    }

    public byte[] bytes() {
        return bytes.clone();
    }

    public BigInteger bigInteger() {
        return bigInteger;
    }

    @Override
    public int compareTo(UINT64 o) {
        return bigInteger.compareTo(o.bigInteger);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UINT64 uint64 = (UINT64) o;

        if (!Arrays.equals(bytes, uint64.bytes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return "UINT64{" + bigInteger + "}";
    }
}
