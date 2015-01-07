package com.theta360.ptp.type;

import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
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
