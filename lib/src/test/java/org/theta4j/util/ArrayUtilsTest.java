/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.util;

import org.junit.Test;
import org.theta4j.TestUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArrayUtilsTest {
    // Constructor

    @Test
    public void isUtilClass() throws Throwable {
        assertTrue(TestUtils.isUtilClass(ArrayUtils.class));
    }

    // join

    @Test(expected = NullPointerException.class)
    public void joinWithNull() {
        // act
        ArrayUtils.join((byte[]) null);
    }

    @Test(expected = NullPointerException.class)
    public void joinWithNull2() {
        // act
        ArrayUtils.join(new byte[0], null);
    }

    @Test
    public void joinWithEmptyArray() {
        // given
        byte[] given1 = new byte[0];
        byte[] given2 = new byte[0];

        // expected
        byte[] expected = new byte[0];

        // act
        byte[] actual = ArrayUtils.join(given1, given2);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void joinWithNoArguments() {
        // expected
        byte[] expected = new byte[0];

        // act
        byte[] actual = ArrayUtils.join();

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
        byte[] actual = ArrayUtils.join(given1, given2);

        // verify
        assertThat(actual, is(expected));
    }
}
