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
public class VersionTest {
    public static class SemverToInt {
        @Test
        public void withMinVersion() {
            String given = "0.0.0";
            Version actual = Version.parse(given);

            assertThat(actual.getName(), is(given));
            assertThat(actual.getCode(), is(0));
        }

        @Test
        public void withMaxVersion() {
            String given = "1023.1023.1023";
            Version actual = Version.parse(given);

            assertThat(actual.getName(), is(given));
            assertThat(actual.getCode(), is(0b00_1111111111_1111111111_1111111111));
        }

        @Test(expected = IllegalArgumentException.class)
        public void withTooBigMajorVersion() {
            Version.parse("1024.0.0");
        }

        @Test(expected = IllegalArgumentException.class)
        public void withTooBigMinorVersion() {
            Version.parse("0.1024.0");
        }

        @Test(expected = IllegalArgumentException.class)
        public void withTooBigPatchVersion() {
            Version.parse("0.0.1024");
        }
    }
}
