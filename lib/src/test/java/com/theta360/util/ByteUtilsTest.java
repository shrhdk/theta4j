package com.theta360.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ByteUtilsTest {
    @Test(expected = NullPointerException.class)
    public void joinWithNull() {
        // act
        ByteUtils.join((byte[]) null);
    }

    @Test(expected = NullPointerException.class)
    public void joinWithNull2() {
        // act
        ByteUtils.join(new byte[0], null);
    }

    @Test
    public void joinWithEmptyArray() {
        // given
        byte[] given1 = new byte[0];
        byte[] given2 = new byte[0];

        // expected
        byte[] expected = new byte[0];

        // act
        byte[] actual = ByteUtils.join(given1, given2);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void joinWithNoArguments() {
        // expected
        byte[] expected = new byte[0];

        // act
        byte[] actual = ByteUtils.join();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void join() {
        // given
        byte[] given1 = new byte[]{0x00, 0x01, 0x02, 0x03};
        byte[] given2 = new byte[]{0x04, 0x05, 0x06, 0x07};

        // expected
        byte[] expected = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        // act
        byte[] actual = ByteUtils.join(given1, given2);

        // verify
        assertThat(actual, is(expected));
    }
}
