/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.type;

import org.theta4j.util.Validators;

import java.math.BigInteger;

/**
 * A base class PTP integer classes.
 */
public abstract class PtpInteger extends Number implements Comparable<PtpInteger> {
    private final byte[] bytes;
    private final BigInteger bigInteger;

    // Constructor

    PtpInteger(long value) {
        this(BigInteger.valueOf(value));
    }

    PtpInteger(BigInteger value) {
        Validators.notNull("value", value);

        if (value.compareTo(min()) < 0 || 0 < value.compareTo(max())) {
            throw new IllegalArgumentException();
        }

        this.bytes = BigIntegerUtils.toLittleEndian(value, sizeInBytes());
        this.bigInteger = value;
    }

    PtpInteger(byte[] bytes) {
        Validators.notNull("bytes", bytes);
        Validators.length("bytes", bytes, sizeInBytes());

        this.bytes = bytes.clone();
        if (isSigned()) {
            this.bigInteger = BigIntegerUtils.asSignedLittleEndian(this.bytes);
        } else {
            this.bigInteger = BigIntegerUtils.asUnsignedLittleEndian(this.bytes);
        }
    }

    // Getter

    public byte[] bytes() {
        return bytes.clone();
    }

    public BigInteger bigInteger() {
        return bigInteger;
    }

    // Min / Max

    private BigInteger min() {
        if (isSigned()) {
            return BigIntegerUtils.minOfSigned(sizeInBytes());
        } else {
            return BigIntegerUtils.minOfUnsigned();
        }
    }

    private BigInteger max() {
        if (isSigned()) {
            return BigIntegerUtils.maxOfSigned(sizeInBytes());
        } else {
            return BigIntegerUtils.maxOfUnsigned(sizeInBytes());
        }
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
    public int compareTo(PtpInteger o) {
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

        PtpInteger rhs = (PtpInteger) o;

        return bigInteger.equals(rhs.bigInteger);
    }

    @Override
    public int hashCode() {
        return bigInteger.hashCode();
    }

    @Override
    public String toString() {
        return BigIntegerUtils.toHexString(bigInteger, sizeInBytes());
    }

    // For Subclasses

    protected abstract int sizeInBytes();

    protected abstract boolean isSigned();
}
