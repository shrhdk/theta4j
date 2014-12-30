package com.theta360.ptp.type;

import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class STRTest {
    private static Charset CHARSET = Charset.forName("UTF-16LE");

    // toBytes

    @Test(expected = NullPointerException.class)
    public void toBytesWithNull() {
        // act
        STR.toBytes(null);
    }

    @Test
    public void toBytesWithEmpty() {
        // given
        String given = "";

        // expected
        byte[] expected = (given + "\u0000").getBytes(CHARSET);

        // act
        byte[] actual = STR.toBytes("");

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
        byte[] actual = STR.toBytes(given);

        // verify
        assertThat(actual, is(expected));
    }

    // toString

    @Test(expected = NullPointerException.class)
    public void toStringWithNull() throws ConvertException {
        STR.toString(null);
    }

    @Test
    public void toStringWithEmpty() throws ConvertException {
        // given
        byte[] given = "\u0000".getBytes(CHARSET);

        // expected
        String expected = "";

        // act
        String actual = STR.toString(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void testToString() throws ConvertException {
        // given
        byte[] given = "test\u0000".getBytes(CHARSET);

        // expected
        String expected = "test";

        // act
        String actual = STR.toString(given);

        // verify
        assertThat(actual, is(expected));
    }
}
