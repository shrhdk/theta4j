package com.theta360.ptp.type;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class INT16 extends LittleEndianInteger {
    // Utility Field

    public static final int SIZE_IN_BYTES = 2;

    public static final INT16 MIN_VALUE = new INT16(BigIntegerUtils.minOfSigned(SIZE_IN_BYTES));
    public static final INT16 MAX_VALUE = new INT16(BigIntegerUtils.maxOfSigned(SIZE_IN_BYTES));

    public static final INT16 ZERO = new INT16((short) 0);

    // Constructor

    public INT16(long value) {
        super(value);
    }

    public INT16(BigInteger value) {
        super(value);
    }

    public INT16(byte[] bytes) {
        super(bytes);
    }

    // Static Factory Method

    public static INT16 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new IOException();
        }

        return new INT16(bytes);
    }

    // LittleEndianInteger

    @Override
    protected int sizeInBytes() {
        return SIZE_IN_BYTES;
    }

    @Override
    protected boolean isSigned() {
        return true;
    }
}
