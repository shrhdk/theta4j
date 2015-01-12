package com.theta360.ptp.type;

import java.util.Arrays;

class UINT {
    private UINT() {
    }

    private static byte[] suppress(byte suppressValue, byte[] bytes) {
        int pos = 0;

        // Skip suppressValue
        for (; pos < bytes.length; pos++) {
            if (bytes[pos] != suppressValue) {
                break;
            }
        }

        // Copy values
        byte[] result = new byte[bytes.length - pos];
        System.arraycopy(bytes, pos, result, 0, result.length);

        return result;
    }

    private static byte[] padding(int sizeInBytes, byte paddingValue, byte[] bytes) {
        if (sizeInBytes < bytes.length) {
            throw new IllegalArgumentException();
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

    public static byte[] toLittleEndian(int sizeInBytes, byte[] unsignedBigEndianBytes) {
        return reverse(padding(sizeInBytes, (byte) 0x00, suppress((byte) 0x00, unsignedBigEndianBytes)));
    }
}
