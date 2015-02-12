package org.theta4j.ptp.type;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * 64 bit unsigned integer value defined in PTP
 */
public final class UINT64 extends PtpInteger {
    // Utility Field

    /**
     * Size of type in bytes.
     */
    public static final int SIZE_IN_BYTES = 8;

    public static final UINT64 MIN_VALUE = new UINT64(BigIntegerUtils.minOfUnsigned(SIZE_IN_BYTES));
    public static final UINT64 MAX_VALUE = new UINT64(BigIntegerUtils.maxOfUnsigned(SIZE_IN_BYTES));

    public static final UINT64 ZERO = new UINT64(0);

    // Constructor

    public UINT64(long value) {
        super(value);
    }

    public UINT64(BigInteger value) {
        super(value);
    }

    public UINT64(byte[] bytes) {
        super(bytes);
    }

    // Static Factory Method

    public static UINT64 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE_IN_BYTES];

        if (is.read(bytes) != SIZE_IN_BYTES) {
            throw new EOFException();
        }

        return new UINT64(bytes);
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
