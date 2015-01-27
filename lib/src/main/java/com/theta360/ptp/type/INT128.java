package com.theta360.ptp.type;

import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class INT128 extends Number implements Comparable<INT128> {
    private static final BigInteger MIN_INTEGER_VALUE = new BigInteger("-10000000000000000000000000000000", 16); // -2**(128-1)
    private static final BigInteger MAX_INTEGER_VALUE = new BigInteger("+7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16); // 2**(128-1)-1

    private final BigInteger bigInteger;
    private final byte[] bytes;

    // Utility Field

    /**
     * Size of type in bytes.
     */
    public static final int SIZE_IN_BYTES = 16;

    public static final INT128 MIN_VALUE = new INT128(MIN_INTEGER_VALUE);
    public static final INT128 MAX_VALUE = new INT128(MAX_INTEGER_VALUE);

    public static final INT128 ZERO = new INT128(0);
    public static final INT128 ONE = new INT128(1);
    public static final INT128 TEN = new INT128(10);

    // Constructor

    public INT128(long value) {
        this(BigInteger.valueOf(value));
    }

    public INT128(BigInteger value) {
        Validators.validateNonNull("value", value);

        if (value.compareTo(MIN_INTEGER_VALUE) < 0 || 0 < value.compareTo(MAX_INTEGER_VALUE)) {
            throw new IllegalArgumentException();
        }

        this.bytes = BigIntegerUtils.toLittleEndian(value, SIZE_IN_BYTES);
        this.bigInteger = value;
    }

    public INT128(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        this.bytes = bytes.clone();

        this.bigInteger = BigIntegerUtils.asSignedLittleEndian(bytes);
    }

    // Static Factory Method

    public static INT128 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new INT128(bytes);
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

    // Compare

    @Override
    public int compareTo(INT128 o) {
        return bigInteger.compareTo(o.bigInteger);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        INT128 int128 = (INT128) o;

        return bigInteger.equals(int128.bigInteger);
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
