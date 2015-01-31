package com.theta360.ptp.type;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * 16 bit unsigned integer value defined in PTP
 */
public final class UINT16 extends LittleEndianInteger {
    // Utility Field

    /**
     * Size of type in bytes.
     */
    public static final int SIZE_IN_BYTES = 2;

    public static final UINT16 MIN_VALUE = new UINT16(BigIntegerUtils.minOfUnsigned(SIZE_IN_BYTES));
    public static final UINT16 MAX_VALUE = new UINT16(BigIntegerUtils.maxOfUnsigned(SIZE_IN_BYTES));

    public static final UINT16 ZERO = new UINT16(0);

    // Constructor

    public UINT16(long value) {
        super(value);
    }

    public UINT16(BigInteger value) {
        super(value);
    }

    public UINT16(byte[] bytes) {
        super(bytes);
    }

    // Static Factory Method

    public static UINT16 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new IllegalArgumentException();
        }

        return new UINT16(bytes);
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
