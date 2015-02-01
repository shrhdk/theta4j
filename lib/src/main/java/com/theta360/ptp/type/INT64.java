package com.theta360.ptp.type;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class INT64 extends LittleEndianInteger {
    // Utility Field

    public static final int SIZE_IN_BYTES = 8;

    public static final INT64 MIN_VALUE = new INT64(BigIntegerUtils.minOfSigned(SIZE_IN_BYTES));
    public static final INT64 MAX_VALUE = new INT64(BigIntegerUtils.maxOfSigned(SIZE_IN_BYTES));

    public static final INT64 ZERO = new INT64(0);

    // Constructor

    public INT64(long value) {
        super(value);
    }

    public INT64(BigInteger value) {
        super(value);
    }

    public INT64(byte[] bytes) {
        super(bytes);
    }

    // Static Factory Method

    public static INT64 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new EOFException();
        }

        return new INT64(bytes);
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
