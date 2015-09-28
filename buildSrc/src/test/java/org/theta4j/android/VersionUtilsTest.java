/*
 * Copyright (C) 2015 theta4j project
 */
package org.theta4j.android;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class VersionUtilsTest {
    public static class SemverToInt {
        @Test
        public void withMinVersion() {
            String given = "0.0.0";
            int actual = VersionUtils.semverToInt(given);
            int expected = 0;

            assertThat(actual, is(expected));
        }

        @Test
        public void withMaxVersion() {
            String given = "1023.1023.1023";
            int actual = VersionUtils.semverToInt(given);
            int expected = 0b00_1111111111_1111111111_1111111111;

            assertThat(actual, is(expected));
        }

        @Test(expected = IllegalArgumentException.class)
        public void withTooBigMajorVersion() {
            VersionUtils.semverToInt("1024.0.0");
        }

        @Test(expected = IllegalArgumentException.class)
        public void withTooBigMinorVersion() {
            VersionUtils.semverToInt("0.1024.0");
        }

        @Test(expected = IllegalArgumentException.class)
        public void withTooBigPatchVersion() {
            VersionUtils.semverToInt("0.0.1024");
        }
    }
}
