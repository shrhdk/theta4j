/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.type;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class INT32 extends PtpInteger {
    // Utility Field

    public static final int SIZE_IN_BYTES = 4;

    public static final INT32 MIN_VALUE = new INT32(BigIntegerUtils.minOfSigned(SIZE_IN_BYTES));
    public static final INT32 MAX_VALUE = new INT32(BigIntegerUtils.maxOfSigned(SIZE_IN_BYTES));

    public static final INT32 ZERO = new INT32(0);

    // Constructor

    public INT32(long value) {
        super(value);
    }

    public INT32(BigInteger value) {
        super(value);
    }

    public INT32(byte[] bytes) {
        super(bytes);
    }

    // Static Factory Method

    public static INT32 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new EOFException();
        }

        return new INT32(bytes);
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
