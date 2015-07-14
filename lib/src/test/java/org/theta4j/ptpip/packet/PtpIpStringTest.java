/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.TestUtils;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class PtpIpStringTest {
    private static final Charset CHARSET = Charset.forName("UTF-16LE");

    public static class Design {
        @Test
        public void isUtilClass() {
            assertTrue(TestUtils.isUtilClass(PtpIpString.class));
        }
    }

    public static class ToBytes {
        @Test(expected = NullPointerException.class)
        public void nullValue() {
            // act
            PtpIpString.toBytes(null);
        }

        @Test
        public void empty() {
            // given
            String given = "";

            // expected
            byte[] expected = (given + "\u0000").getBytes(CHARSET);

            // act
            byte[] actual = PtpIpString.toBytes("");

            // verify
            assertThat(actual, is(expected));
        }

        @Test
        public void normal() {
            // given
            String given = "test";

            // expected
            byte[] expected = (given + "\u0000").getBytes(CHARSET);

            // act
            byte[] actual = PtpIpString.toBytes(given);

            // verify
            assertThat(actual, is(expected));
        }
    }

    public static class Read {
        @Test(expected = NullPointerException.class)
        public void nullValue() throws IOException {
            PtpIpString.read(null);
        }

        @Test(expected = EOFException.class)
        public void readWithoutTerminator() throws IOException {
            // given
            byte[] given = "test".getBytes(CHARSET);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given);

            // act
            PtpIpString.read(givenInputStream);
        }

        @Test(expected = EOFException.class)
        public void oddLengthByteArray() throws IOException {
            // given
            InputStream given = new ByteArrayInputStream(new byte[]{0x00});

            // act
            PtpIpString.read(given);
        }

        @Test
        public void empty() throws IOException {
            // given
            byte[] given = "\u0000".getBytes(CHARSET);

            // expected
            String expected = "";

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given);

            // act
            String actual = PtpIpString.read(givenInputStream);

            // verify
            assertThat(actual, is(expected));
        }

        @Test
        public void normal() throws IOException {
            // given
            byte[] given = "test\u0000".getBytes(CHARSET);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given);

            // expected
            String expected = "test";

            // act
            String actual = PtpIpString.read(givenInputStream);

            // verify
            assertThat(actual, is(expected));
        }
    }
}
