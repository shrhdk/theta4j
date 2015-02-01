package com.theta360.ptp.type;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public final class UINT128 extends LittleEndianInteger {
    // Utility Field

    /**
     * Size of type in bytes.
     */
    public static final int SIZE_IN_BYTES = 16;

    public static final UINT128 MIN_VALUE = new UINT128(BigIntegerUtils.minOfUnsigned(SIZE_IN_BYTES));
    public static final UINT128 MAX_VALUE = new UINT128(BigIntegerUtils.maxOfUnsigned(SIZE_IN_BYTES));

    public static final UINT128 ZERO = new UINT128(0);

    // Constructor

    public UINT128(long value) {
        super(value);
    }

    public UINT128(BigInteger value) {
        super(value);
    }

    public UINT128(byte[] bytes) {
        super(bytes);
    }

    // Static Factory Method

    public static UINT128 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new EOFException();
        }

        return new UINT128(bytes);
    }

    // LittleEndianInteger

    @Override
    protected int sizeInBytes() {
        return SIZE_IN_BYTES;
    }

    @Override
    protected boolean isSigned() {
        return false;
    }
}
