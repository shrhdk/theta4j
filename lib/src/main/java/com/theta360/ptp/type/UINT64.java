package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * 64 bit unsigned integer value defined in PTP
 */
public final class UINT64 extends Number implements Comparable<UINT64> {
    private static final BigInteger MIN_INTEGER_VALUE = BigInteger.ZERO;
    private static final BigInteger MAX_INTEGER_VALUE = new BigInteger("00FFFFFFFFFFFFFFFF", 16);

    private final BigInteger bigInteger;
    private final byte[] bytes;

    // Utility Field

    /**
     * Size of type in bytes.
     */
    public static final int SIZE_IN_BYTES = 8;

    public static final UINT64 MIN_VALUE = new UINT64(MIN_INTEGER_VALUE);
    public static final UINT64 MAX_VALUE = new UINT64(MAX_INTEGER_VALUE);

    public static final UINT64 ZERO = new UINT64(0);
    public static final UINT64 ONE = new UINT64(1);
    public static final UINT64 TEN = new UINT64(10);

    // Constructor

    public UINT64(long value) {
        this(BigInteger.valueOf(value));
    }

    public UINT64(BigInteger value) {
        Validators.validateNonNull("value", value);

        if (value.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (0 < value.compareTo(MAX_INTEGER_VALUE)) {
            throw new IllegalArgumentException();
        }

        this.bytes = BigIntegerUtils.toLittleEndian(value, SIZE_IN_BYTES);
        this.bigInteger = value;
    }

    public UINT64(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.bytes = bytes.clone();

        this.bigInteger = BigIntegerUtils.asUnsignedLittleEndian(bytes);
    }

    // Static Factory Method

    public static UINT64 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new UINT64(bytes);
    }

    // Getter

    public byte[] bytes() {
        return bytes.clone();
    }

    public BigInteger bigInteger() {
        return bigInteger;
    }

    // Number

    @Override
    public int intValue() {
        return bigInteger.intValue();
    }

    @Override
    public long longValue() {
        return bigInteger.longValue();
    }

    @Override
    public float floatValue() {
        return bigInteger.floatValue();
    }

    @Override
    public double doubleValue() {
        return bigInteger.doubleValue();
    }

    // Comparable

    @Override
    public int compareTo(UINT64 o) {
        return bigInteger.compareTo(o.bigInteger);
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

        UINT64 rhs = (UINT64) o;

        return bigInteger.equals(rhs.bigInteger);
    }

    @Override
    public int hashCode() {
        return bigInteger.hashCode();
    }

    @Override
    public String toString() {
        return bigInteger.toString();
    }
}
