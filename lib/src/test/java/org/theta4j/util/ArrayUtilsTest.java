/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.util;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.TestUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class ArrayUtilsTest {
    public static class Design {
        @Test
        public void isUtilClass() throws Throwable {
            assertTrue(TestUtils.isUtilClass(ArrayUtils.class));
        }
    }

    public static class Join {
        @Test(expected = NullPointerException.class)
        public void withNull() {
            // act
            ArrayUtils.join((byte[]) null);
        }

        @Test(expected = NullPointerException.class)
        public void withNull2() {
            // act
            ArrayUtils.join(new byte[0], null);
        }

        @Test
        public void withEmptyArray() {
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
        public void withNoArguments() {
            // expected
            byte[] expected = new byte[0];

            // act
            byte[] actual = ArrayUtils.join();

            // verify
            assertThat(actual, is(expected));
        }

        @Test
        public void normal() {
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
}
