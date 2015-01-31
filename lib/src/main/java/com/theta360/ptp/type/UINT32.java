package com.theta360.ptp.type;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * 32 bit unsigned integer value defined in PTP
 */
public final class UINT32 extends LittleEndianInteger {
    // Utility Field

    /**
     * Size of type in bytes.
     */
    public static final int SIZE_IN_BYTES = 4;

    public static final UINT32 MIN_VALUE = new UINT32(BigIntegerUtils.minOfUnsigned(SIZE_IN_BYTES));
    public static final UINT32 MAX_VALUE = new UINT32(BigIntegerUtils.maxOfUnsigned(SIZE_IN_BYTES));

    public static final UINT32 ZERO = new UINT32(0);

    // Constructor

    public UINT32(long value) {
        super(value);
    }

    public UINT32(BigInteger value) {
        super(value);
    }

    public UINT32(byte[] bytes) {
        super(bytes);
    }

    // Static Factory Method

    public static UINT32 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new IOException();
        }

        return new UINT32(bytes);
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
