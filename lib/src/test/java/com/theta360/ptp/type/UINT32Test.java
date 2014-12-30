package com.theta360.ptp.type;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class UINT32Test {
    // Construct from long value

    @Test(expected = IllegalArgumentException.class)
    public void fromNegativeLong() {
        // act
        new UINT32(-1);
    }

    @Test
    public void zeroFromLong() {
        // given
        UINT32 actual = new UINT32(0);

        // expected
        long expectedLong = 0;
        byte[] expectedBytes = new byte[]{0x00, 0x00, 0x00, 0x00};

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void positiveValueFromLong() {
        // given
        UINT32 actual = new UINT32(1);

        // expected
        long expectedLong = 1;
        byte[] expectedBytes = new byte[]{0x01, 0x00, 0x00, 0x00};

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void maxValueFromLong() {
        // given
        UINT32 actual = new UINT32(UINT32.MAX_VALUE);

        // expected
        long expectedLong = UINT32.MAX_VALUE;
        byte[] expectedBytes = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    // Construct from bytes

    @Test
    public void zeroFromBytes() {
        // given
        UINT32 actual = new UINT32(0x00, 0x00, 0x00, 0x00);

        // expected
        long expectedLong = 0;
        byte[] expectedBytes = new byte[]{0x00, 0x00, 0x00, 0x00};

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void positiveValueFromBytes() {
        // given
        UINT32 actual = new UINT32(0x01, 0x00, 0x00, 0x00);

        // expected
        long expectedLong = 1;
        byte[] expectedBytes = new byte[]{0x01, 0x00, 0x00, 0x00};

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void maxValueFromBytes() {
        // given
        UINT32 actual = new UINT32(0xFF, 0xFF, 0xFF, 0xFF);

        // expected
        long expectedLong = UINT32.MAX_VALUE;
        byte[] expectedBytes = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    // Construct from byte array

    @Test
    public void zeroFromByteArray() {
        // given
        byte[] givenArray = new byte[]{0x00, 0x00, 0x00, 0x00};

        // expected
        long expectedLong = 0;
        byte[] expectedBytes = new byte[]{0x00, 0x00, 0x00, 0x00};

        // act
        UINT32 actual = new UINT32(givenArray);

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void positiveValueFromByteArray() {
        // given
        byte[] givenArray = new byte[]{0x01, 0x00, 0x00, 0x00};

        // expected
        long expectedLong = 1;
        byte[] expectedBytes = new byte[]{0x01, 0x00, 0x00, 0x00};

        // act
        UINT32 actual = new UINT32(givenArray);

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    @Test
    public void maxValueFromByteArray() {
        // given
        byte[] givenArray = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        // expected
        long expectedLong = UINT32.MAX_VALUE;
        byte[] expectedBytes = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        // act
        UINT32 actual = new UINT32(givenArray);

        // verify
        assertThat(actual.longValue(), is(expectedLong));
        assertThat(actual.bytes(), is(expectedBytes));
    }

    // Basic method

    @Test
    public void compare() {
        // given
        UINT32 v1 = new UINT32(1);
        UINT32 v2 = new UINT32(2);
        UINT32 v3 = new UINT32(3);

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
        UINT32 v1 = new UINT32(1);
        UINT32 v2 = new UINT32(2);
        UINT32 v3 = new UINT32(3);

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
        UINT32 v1 = new UINT32(1);
        UINT32 v2 = new UINT32(2);
        UINT32 v3 = new UINT32(3);

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
}
