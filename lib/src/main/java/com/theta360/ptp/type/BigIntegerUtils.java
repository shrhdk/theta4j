package com.theta360.ptp.type;

import com.theta360.util.ArrayUtils;
import com.theta360.util.Validators;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;

class BigIntegerUtils {
    private BigIntegerUtils() {
        throw new AssertionError();
    }

    private static byte[] suppress(byte suppressValue, byte[] bytes) {
        int pos = 0;

        // Skip suppressValue
        for (; pos < bytes.length - 1; pos++) {
            if (bytes[pos] != suppressValue) {
                break;
            }
        }

        // Copy values
        byte[] result = new byte[bytes.length - pos];
        System.arraycopy(bytes, pos, result, 0, result.length);

        return result;
    }

    private static byte[] padding(byte paddingValue, byte[] bytes, int sizeInBytes) {
        if (sizeInBytes < bytes.length) {
            String message = String.format("Length of specified bytes (%d) is larger than specified size (%d)", bytes.length, sizeInBytes);
            throw new IllegalArgumentException(message);
        }

        if (sizeInBytes == bytes.length) {
            return bytes.clone();
        }

        int sizeToPadding = sizeInBytes - bytes.length;
        byte[] result = new byte[sizeInBytes];

        Arrays.fill(result, 0, sizeToPadding - 1, paddingValue);
        System.arraycopy(bytes, 0, result, sizeToPadding, bytes.length);

        return result;
    }

    private static byte[] reverse(byte[] bytes) {
        byte[] reversed = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            int j = reversed.length - 1 - i;
            reversed[j] = bytes[i];
        }
        return reversed;
    }

    public static byte[] toLittleEndian(BigInteger integer, int sizeInBytes) {
        Validators.validateNonNull("integer", integer);

        boolean isPositive = 0 <= integer.signum();
        byte[] bigEndian = integer.toByteArray();

        // If the value is a positive number,
        // there is a possibility that byte array is beginning with unnecessary 0x00,
        // because it is two's complement.
        // It needs to suppress unnecessary 0x00 when handle byte array as unsigned value.
        boolean hasUnnecessaryZero = isPositive && bigEndian[0] == 0x00;

        if (hasUnnecessaryZero) {
            bigEndian = suppress((byte) 0x00, bigEndian);
        }

        // Sign Extension
        if (isPositive) {
            bigEndian = padding((byte) 0x00, bigEndian, sizeInBytes);
        } else {
            bigEndian = padding((byte) 0xFF, bigEndian, sizeInBytes);
        }

        return reverse(bigEndian);
    }

    /**
     * Convert the signed little endian byte array to BigInteger.
     *
     * @param bytes signed little endian byte array
     */
    public static BigInteger asSignedLittleEndian(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        byte[] bigEndian = reverse(bytes);

        return new BigInteger(bigEndian);
    }

    /**
     * Convert the unsigned little endian byte array to BigInteger.
     *
     * @param bytes unsigned little endian byte array
     */
    public static BigInteger asUnsignedLittleEndian(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        byte[] bigEndian = reverse(bytes);

        // UINT64 constructor arguments are little endian and are not two's complement.
        // BigInteger constructor needs big endian two's complement value.
        // So it need to reverse order of arguments and add 0x00 to top.
        bigEndian = ArrayUtils.join(new byte[]{0x00}, bigEndian);

        return new BigInteger(bigEndian);
    }

    public static BigInteger minOfSigned(int size) {
        return new BigInteger("-80" + StringUtils.repeat("00", size - 1), 16);
    }

    public static BigInteger maxOfSigned(int size) {
        return new BigInteger("+7f" + StringUtils.repeat("ff", size - 1), 16);
    }

    public static BigInteger minOfUnsigned(int size) {
        return BigInteger.ZERO;
    }

    public static BigInteger maxOfUnsigned(int size) {
        return new BigInteger("+ff" + StringUtils.repeat("ff", size - 1), 16);
    }

    public static String toHexString(BigInteger integer, int size) {
        Validators.validateNonNull("integer", integer);
        if (size < 1) {
            throw new IllegalArgumentException();
        }

        int digits = size * 2;
        String raw = integer.toString(16);

        boolean isPositive = 0 <= integer.signum();

        if (isPositive) {
            return "0x" + StringUtils.leftPad(raw, digits, '0');
        } else {
            String withoutSign = StringUtils.removeStart(raw, "-");
            return "0x" + StringUtils.leftPad(withoutSign, digits, '0');
        }
    }
}
