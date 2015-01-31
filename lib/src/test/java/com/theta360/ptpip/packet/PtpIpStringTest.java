package com.theta360.ptpip.packet;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PtpIpStringTest {
    private static Charset CHARSET = Charset.forName("UTF-16LE");

    // toBytes

    @Test(expected = NullPointerException.class)
    public void toBytesWithNull() {
        // act
        PtpIpString.toBytes(null);
    }

    @Test
    public void toBytesWithEmpty() {
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
    public void toBytes() {
        // given
        String given = "test";

        // expected
        byte[] expected = (given + "\u0000").getBytes(CHARSET);

        // act
        byte[] actual = PtpIpString.toBytes(given);

        // verify
        assertThat(actual, is(expected));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readWithNull() throws IOException {
        PtpIpString.read(null);
    }


    @Test(expected = RuntimeException.class)
    public void readInvalidName() throws IOException {
        // given
        byte[] invalidNameBytes = new byte[]{0x01};  // Not end with 0x00

        // arrange
        InputStream pis = new ByteArrayInputStream(invalidNameBytes);

        // act
        PtpIpString.read(pis);
    }

    // read

    @Test
    public void readWithEmpty() throws IOException {
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
    public void read() throws IOException {
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
