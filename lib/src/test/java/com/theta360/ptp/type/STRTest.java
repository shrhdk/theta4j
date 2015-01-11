package com.theta360.ptp.type;

import com.theta360.util.ByteUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        byte[] expected = new byte[]{0};

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
        byte[] expected = ByteUtils.join(
                new byte[]{(byte) given.length()},
                given.getBytes(CHARSET)
        );

        // act
        byte[] actual = STR.toBytes(given);

        // verify
        assertThat(actual, is(expected));
    }

    // read

    @Test(expected = NullPointerException.class)
    public void readWithNull() throws IOException {
        STR.read(null);
    }

    @Test
    public void readWithEmpty() throws IOException {
        // given
        byte[] given = new byte[]{0};

        // expected
        String expected = "";

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        String actual = STR.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void read() throws IOException {
        // given
        String given = "test";

        // arrange
        byte[] givenBytes = ByteUtils.join(
                new byte[]{(byte) given.length()},
                given.getBytes(CHARSET)
        );
        InputStream givenInputStream = new ByteArrayInputStream(givenBytes);

        // act
        String actual = STR.read(givenInputStream);

        // verify
        assertThat(actual, is(given));
    }
}
