package com.theta360.ptp.type;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * 64 bit unsigned integer value defined in PTP
 */
public final class UINT64 implements Comparable<UINT64> {
    private static final BigInteger MIN_INTEGER_VALUE = BigInteger.ZERO;
    private static final BigInteger MAX_INTEGER_VALUE = new BigInteger("00FFFFFFFFFFFFFFFF", 16);

    /**
     * Size of type in bytes.
     */
    public static final int SIZE = 8;

    public static final UINT64 MIN_VALUE = new UINT64(MIN_INTEGER_VALUE);
    public static final UINT64 MAX_VALUE = new UINT64(MAX_INTEGER_VALUE);

    public static final UINT64 ZERO = new UINT64(0);
    public static final UINT64 ONE = new UINT64(1);
    public static final UINT64 TEN = new UINT64(10);

    private final BigInteger bigInteger;
    private final byte[] bytes;

    // Constructor

    public UINT64(long value) {
        this(BigInteger.valueOf(value));
    }

    public UINT64(BigInteger value) {
        Validators.validateNonNull("value", value);

        if (value.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (0 < value.compareTo(MAX_INTEGER_VALUE)) {
            throw new IllegalArgumentException();
        }

        this.bytes = UINT.toLittleEndian(8, value.toByteArray());
        this.bigInteger = value;
    }

    // Private Constructor

    private UINT64(byte[] bytes) {
        if (bytes.length != 8) {
            throw new IllegalArgumentException();
        }

        this.bytes = bytes.clone();

        // UINT64 constructor arguments are little endian and are not two's complement.
        // BigInteger constructor needs big endian two's complement value.
        // So it need to reverse order of arguments and add 0x00 to top.
        this.bigInteger = new BigInteger(new byte[]{
                0x00, bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]
        });
    }

    // Getter

    public byte[] bytes() {
        return bytes.clone();
    }

    public BigInteger bigInteger() {
        return bigInteger;
    }

    // Basic Method

    @Override
    public int compareTo(UINT64 o) {
        return bigInteger.compareTo(o.bigInteger);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UINT64 uint64 = (UINT64) o;

        if (!Arrays.equals(bytes, uint64.bytes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return "UINT64{" + bigInteger + "}";
    }

    // Static Factory Method

    public static UINT64 valueOf(byte[] bytes) throws IOException {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE);

        try (PtpInputStream pis = new PtpInputStream(bytes)) {
            return read(pis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static UINT64 read(InputStream is) throws IOException {
        byte[] bytes = new byte[SIZE];

        if (is.read(bytes) == -1) {
            throw new IOException();
        }

        return new UINT64(bytes);
    }
}
