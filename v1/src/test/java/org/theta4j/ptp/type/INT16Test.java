/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.type;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class INT16Test {
    private static final BigInteger MIN_INTEGER_VALUE = BigIntegerUtils.minOfSigned(INT16.SIZE_IN_BYTES);
    private static final BigInteger MAX_INTEGER_VALUE = BigIntegerUtils.maxOfSigned(INT16.SIZE_IN_BYTES);

    private static final INT16 V1 = new INT16(1);
    private static final INT16 V2 = new INT16(2);
    private static final INT16 V3 = new INT16(3);

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void constructWithNullInteger() {
        // act
        new INT16((BigInteger) null);
    }

    @Test(expected = NullPointerException.class)
    public void constructWithNullBytes() {
        // act
        new INT16((byte[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithTooShortValue() {
        // act
        new INT16(MIN_INTEGER_VALUE.subtract(BigInteger.ONE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithTooBigValue() {
        // act
        new INT16(MAX_INTEGER_VALUE.add(BigInteger.ONE));
    }

    // Construct and Get

    @Test
    public void constructAndGet() {
        // given
        BigInteger given = BigInteger.ONE;

        // expected
        BigInteger expectedInteger = BigInteger.ONE;
        byte[] expectedBytes = new byte[]{0x01, 0x00};

        // act
        INT16 actual = new INT16(given);

        // verify
        assertThat(actual.bigInteger(), is(expectedInteger));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void constructWithZeroAndGet() {
        // given
        BigInteger given = BigInteger.ZERO;

        // expected
        BigInteger expectedInteger = BigInteger.ZERO;
        byte[] expectedBytes = new byte[]{0x00, 0x00};

        // act
        INT16 actual = new INT16(given);

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
        byte[] expectedBytes = new byte[]{0x00, (byte) 0x80};

        // act
        INT16 actual = new INT16(given);

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
        byte[] expectedBytes = new byte[]{(byte) 0xff, (byte) 0x7F};

        // act
        INT16 actual = new INT16(given);

        // verify
        assertThat(actual.bigInteger(), is(expectedInteger));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        INT16.read(null);
    }

    @Test(expected = EOFException.class)
    public void readTooShortInputStream() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new byte[INT16.SIZE_IN_BYTES - 1]);

        // act
        INT16.read(given);
    }

    // read

    @Test
    public void readZero() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new INT16(BigInteger.valueOf(0)).bytes());

        // expected
        INT16 expected = new INT16(BigInteger.valueOf(0));

        // act
        INT16 actual = INT16.read(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readPositiveValue() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new INT16(1).bytes());

        // expected
        INT16 expected = new INT16(1);

        // act
        INT16 actual = INT16.read(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readMaxValue() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(INT16.MAX_VALUE.bytes());

        // expected
        INT16 expected = INT16.MAX_VALUE;

        // act
        INT16 actual = INT16.read(given);

        // verify
        assertThat(actual, is(expected));
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

    // toString

    @Test
    public void testToString() {
        assertThat(V1.toString(), is("0x0001"));
        assertThat(V2.toString(), is("0x0002"));
        assertThat(V3.toString(), is("0x0003"));
        assertThat(INT16.ZERO.toString(), is("0x0000"));
        assertThat(INT16.MIN_VALUE.toString(), is("0x8000"));
        assertThat(INT16.MAX_VALUE.toString(), is("0x7fff"));
    }
}
