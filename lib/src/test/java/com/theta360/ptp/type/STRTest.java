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

    // valueOf

    @Test(expected = NullPointerException.class)
    public void valueOfNull() {
        // act
        STR.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfEmptyBytes() {
        // act
        STR.valueOf(new byte[]{});
    }

    @Test
    public void valueOfEmpty() {
        // given
        byte[] given = new byte[]{0x00};

        // expected
        String expected = "";

        // act
        String actual = STR.valueOf(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfInvalidBytes() {
        // given (String Chars does not consist of not 2x bytes.)
        byte[] given = new byte[]{0x01, 0x00};

        // act
        STR.valueOf(given);
    }

    @Test
    public void valueOf() {
        // given
        String given = "test";

        // arrange
        byte[] givenBytes = ByteUtils.join(
                new byte[]{(byte) given.length()},
                given.getBytes(CHARSET)
        );

        // act
        String actual = STR.valueOf(givenBytes);

        // verify
        assertThat(actual, is(given));

    }

    // read

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        STR.read(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readEmptyBytes() throws IOException {
        // given
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        STR.read(givenInputStream);
    }

    @Test
    public void readEmpty() throws IOException {
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

    @Test(expected = IllegalArgumentException.class)
    public void readInvalidBytes() throws IOException {
        // given (String Chars does not consist of not 2x bytes.)
        byte[] given = new byte[]{0x01, 0x00};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        STR.read(givenInputStream);
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
