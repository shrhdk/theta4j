package com.theta360.ptp.type;

import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@Category(UnitTest.class)
public class UINT64Test {
    private static final BigInteger MAX_INTEGER_VALUE = new BigInteger("00FFFFFFFFFFFFFFFF", 16);

    private static final UINT64 V1 = new UINT64(1);
    private static final UINT64 V2 = new UINT64(2);
    private static final UINT64 V3 = new UINT64(3);

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void constructWithNull() {
        // act
        new UINT64(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithNegativeValue() {
        new UINT64(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructWithTooBigValue() {
        new UINT64(MAX_INTEGER_VALUE.add(BigInteger.ONE));
    }

    // Construct and Get
    @Test
    public void constructWithZeroAndGet() {
        // given
        BigInteger given = BigInteger.valueOf(0);

        // expected
        BigInteger expectedInteger = BigInteger.valueOf(0);
        byte[] expectedBytes = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        // act
        UINT64 actual = new UINT64(given);

        // verify
        assertThat(actual.bigInteger(), is(expectedInteger));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void constructWithPositiveValueAndGet() {
        // given
        BigInteger given = BigInteger.valueOf(1);

        // expected
        BigInteger expectedInteger = BigInteger.valueOf(1);
        byte[] expectedBytes = new byte[]{0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

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
}
