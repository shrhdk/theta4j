package com.theta360.ptp.type;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class UINT64Test {
    private static final BigInteger MIN_INTEGER_VALUE = BigIntegerUtils.minOfUnsigned(UINT64.SIZE_IN_BYTES);
    private static final BigInteger MAX_INTEGER_VALUE = BigIntegerUtils.maxOfUnsigned(UINT64.SIZE_IN_BYTES);

    private static final UINT64 V1 = new UINT64(1);
    private static final UINT64 V2 = new UINT64(2);
    private static final UINT64 V3 = new UINT64(3);

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void constructWithNullInteger() {
        // act
        new UINT64((BigInteger) null);
    }

    @Test(expected = NullPointerException.class)
    public void constructWithNullBytes() {
        // act
        new UINT64((byte[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithTooShortValue() {
        // act
        new UINT64(MIN_INTEGER_VALUE.subtract(BigInteger.ONE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithTooBigValue() {
        // act
        new UINT64(MAX_INTEGER_VALUE.add(BigInteger.ONE));
    }

    // Construct and Get

    @Test
    public void constructAndGet() {
        // given
        BigInteger given = BigInteger.ONE;

        // expected
        BigInteger expectedInteger = BigInteger.ONE;
        byte[] expectedBytes = new byte[]{0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        // act
        UINT64 actual = new UINT64(given);

        // verify
        assertThat(actual.bigInteger(), is(expectedInteger));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void constructWithMinValueAndGet() {
        // given
        BigInteger given = MIN_INTEGER_VALUE;

        // expected
        BigInteger expectedInteger = MIN_INTEGER_VALUE;
        byte[] expectedBytes = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        // act
        UINT64 actual = new UINT64(given);

        // verify
        assertThat(actual.bigInteger(), is(expectedInteger));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void constructWithMaxValueAndGet() {
        // given
        BigInteger given = MAX_INTEGER_VALUE;

        // expected
        BigInteger expectedInteger = MAX_INTEGER_VALUE;
        byte[] expectedBytes = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        // act
        UINT64 actual = new UINT64(given);

        // verify
        assertThat(actual.bigInteger(), is(expectedInteger));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    // Basic method

    @Test
    public void compare() {
        // verify
        assertThat(V1.compareTo(V1), is(0));
        assertThat(V1.compareTo(V2), is(-1));
        assertThat(V1.compareTo(V3), is(-1));

        // verify
        assertThat(V2.compareTo(V1), is(1));
        assertThat(V2.compareTo(V2), is(0));
        assertThat(V2.compareTo(V3), is(-1));

        // verify
        assertThat(V3.compareTo(V1), is(1));
        assertThat(V3.compareTo(V2), is(1));
        assertThat(V3.compareTo(V3), is(0));
    }

    @Test
    public void testHashCode() {
        // verify
        assertThat(V1.hashCode(), is(V1.hashCode()));
        assertThat(V1.hashCode(), is(not(V2.hashCode())));
        assertThat(V1.hashCode(), is(not(V3.hashCode())));

        // verify
        assertThat(V2.hashCode(), is(not(V1.hashCode())));
        assertThat(V2.hashCode(), is(V2.hashCode()));
        assertThat(V2.hashCode(), is(not(V3.hashCode())));

        // verify
        assertThat(V3.hashCode(), is(not(V1.hashCode())));
        assertThat(V3.hashCode(), is(not(V2.hashCode())));
        assertThat(V3.hashCode(), is(V3.hashCode()));
    }

    @Test
    public void testEquals() {
        // verify with null
        assertFalse(V1.equals(null));
        assertFalse(V2.equals(null));
        assertFalse(V3.equals(null));

        // verify with different class
        assertFalse(V1.equals("foo"));
        assertFalse(V2.equals("foo"));
        assertFalse(V3.equals("foo"));

        // verify
        assertTrue(V1.equals(V1));
        assertFalse(V1.equals(V2));
        assertFalse(V1.equals(V3));

        // verify
        assertFalse(V2.equals(V1));
        assertTrue(V2.equals(V2));
        assertFalse(V2.equals(V3));

        // verify
        assertFalse(V3.equals(V1));
        assertFalse(V3.equals(V2));
        assertTrue(V3.equals(V3));
    }

    // read

    @Test(expected = IOException.class)
    public void readTooShortInputStream() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new byte[UINT64.SIZE_IN_BYTES - 1]);

        // act
        UINT64.read(given);
    }

    @Test
    public void readZero() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new UINT64(BigInteger.valueOf(0)).bytes());

        // expected
        UINT64 expected = new UINT64(BigInteger.valueOf(0));

        // act
        UINT64 actual = UINT64.read(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readPositiveValue() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new UINT64(1).bytes());

        // expected
        UINT64 expected = new UINT64(1);

        // act
        UINT64 actual = UINT64.read(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readMaxValue() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(UINT64.MAX_VALUE.bytes());

        // expected
        UINT64 expected = UINT64.MAX_VALUE;

        // act
        UINT64 actual = UINT64.read(given);

        // verify
        assertThat(actual, is(expected));
    }

    // toString

    @Test
    public void testToString() {
        assertThat(UINT64.ZERO.toString(), is("0x0000000000000000"));
        assertThat(V1.toString(), is("0x0000000000000001"));
        assertThat(V2.toString(), is("0x0000000000000002"));
        assertThat(V3.toString(), is("0x0000000000000003"));
        assertThat(UINT64.MAX_VALUE.toString(), is("0xffffffffffffffff"));
    }
}
