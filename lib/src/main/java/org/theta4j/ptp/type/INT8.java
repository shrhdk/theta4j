/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.type;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public final class INT8 extends PtpInteger {
    // Utility Field

    public static final int SIZE_IN_BYTES = 1;

    public static final INT8 MIN_VALUE = new INT8(BigIntegerUtils.minOfSigned(SIZE_IN_BYTES));
    public static final INT8 MAX_VALUE = new INT8(BigIntegerUtils.maxOfSigned(SIZE_IN_BYTES));

    public static final INT8 ZERO = new INT8((byte) 0);

    // Constructor

    public INT8(long value) {
        super(value);
    }

    public INT8(BigInteger value) {
        super(value);
    }

    public INT8(byte[] bytes) {
        super(bytes);
    }


    // Static Factory Method

    public static INT8 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new EOFException();
        }

        return new INT8(bytes);
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
