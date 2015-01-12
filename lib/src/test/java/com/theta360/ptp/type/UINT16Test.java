package com.theta360.ptp.type;

import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@Category(UnitTest.class)
public class UINT16Test {
    // Construct with error

    @Test(expected = IllegalArgumentException.class)
    public void constructWithNegativeValue() {
        // act
        new UINT16(-1);
    }

    // Construct and Get

    @Test
    public void constructWithZeroAndGet() {
        // given
        int given = 0;

        // expected
        int expectedInt = 0;
        byte[] expectedBytes = new byte[]{0x00, 0x00};

        // act
        UINT16 actual = new UINT16(given);

        // verify
        assertThat(actual.intValue(), is(expectedInt));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void constructWithPositiveValueAndGet() {
        // given
        int given = 1;

        // expected
        int expectedInt = 1;
        byte[] expectedBytes = new byte[]{0x01, 0x00};

        // act
        UINT16 actual = new UINT16(given);

        // verify
        assertThat(actual.intValue(), is(expectedInt));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void constructWithMaxValueAndGet() {
        // given
        int given = UINT16.MAX_VALUE.intValue();

        // expected
        int expectedInt = UINT16.MAX_VALUE.intValue();
        byte[] expectedBytes = new byte[]{(byte) 0xFF, (byte) 0xFF};

        // act
        UINT16 actual = new UINT16(given);

        // verify
        assertThat(actual.intValue(), is(expectedInt));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    // Basic method

    @Test
    public void compare() {
        // given
        UINT16 v1 = new UINT16(1);
        UINT16 v2 = new UINT16(2);
        UINT16 v3 = new UINT16(3);

        // verify
        assertThat(v1.compareTo(v1), is(0));
        assertThat(v1.compareTo(v2), is(-1));
        assertThat(v1.compareTo(v3), is(-1));

        // verify
        assertThat(v2.compareTo(v1), is(1));
        assertThat(v2.compareTo(v2), is(0));
        assertThat(v2.compareTo(v3), is(-1));

        // verify
        assertThat(v3.compareTo(v1), is(1));
        assertThat(v3.compareTo(v2), is(1));
        assertThat(v3.compareTo(v3), is(0));
    }

    @Test
    public void testHashCode() {
        // given
        UINT16 v1 = new UINT16(1);
        UINT16 v2 = new UINT16(2);
        UINT16 v3 = new UINT16(3);

        // verify
        assertThat(v1.hashCode(), is(v1.hashCode()));
        assertThat(v1.hashCode(), is(not(v2.hashCode())));
        assertThat(v1.hashCode(), is(not(v3.hashCode())));

        // verify
        assertThat(v2.hashCode(), is(not(v1.hashCode())));
        assertThat(v2.hashCode(), is(v2.hashCode()));
        assertThat(v2.hashCode(), is(not(v3.hashCode())));

        // verify
        assertThat(v3.hashCode(), is(not(v1.hashCode())));
        assertThat(v3.hashCode(), is(not(v2.hashCode())));
        assertThat(v3.hashCode(), is(v3.hashCode()));
    }

    @Test
    public void testEquals() {
        // given
        UINT16 v1 = new UINT16(1);
        UINT16 v2 = new UINT16(2);
        UINT16 v3 = new UINT16(3);

        // verify
        assertTrue(v1.equals(v1));
        assertFalse(v1.equals(v2));
        assertFalse(v1.equals(v3));

        // verify
        assertFalse(v2.equals(v1));
        assertTrue(v2.equals(v2));
        assertFalse(v2.equals(v3));

        // verify
        assertFalse(v3.equals(v1));
        assertFalse(v3.equals(v2));
        assertTrue(v3.equals(v3));
    }

    // read

    @Test
    public void readZero() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new UINT16(0).bytes());

        // expected
        UINT16 expected = new UINT16(0);

        // act
        UINT16 actual = UINT16.read(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readPositiveValue() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new UINT16(1).bytes());

        // expected
        UINT16 expected = new UINT16(1);

        // act
        UINT16 actual = UINT16.read(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readMaxValue() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(UINT16.MAX_VALUE.bytes());

        // expected
        UINT16 expected = UINT16.MAX_VALUE;

        // act
        UINT16 actual = UINT16.read(given);

        // verify
        assertThat(actual, is(expected));
    }
}
