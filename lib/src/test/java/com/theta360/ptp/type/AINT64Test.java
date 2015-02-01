package com.theta360.ptp.type;

import com.theta360.util.ByteUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AINT64Test {
    // valueOf

    @Test(expected = NullPointerException.class)
    public void valueOfNull() {
        // act
        AINT64.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfEmptyBytes() {
        // act
        AINT64.valueOf(new byte[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfInvalidBytes() {
        // given
        byte[] given = new byte[]{0x00};

        // act
        AINT64.valueOf(given);
    }

    @Test
    public void valueOfEmpty() {
        // given
        byte[] given = UINT32.ZERO.bytes();

        // expected
        List<INT64> expected = new ArrayList<>();

        // act
        List<INT64> actual = AINT64.valueOf(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void valueOf() {
        // given
        INT64 given = INT64.MAX_VALUE;

        // expected
        List<INT64> expected = new ArrayList<>();
        expected.add(given);

        // arrange
        byte[] givenBytes = ByteUtils.join(
                new UINT32(1).bytes(),
                given.bytes()
        );

        // act
        List<INT64> actual = AINT64.valueOf(givenBytes);

        // verify
        assertThat(actual, is(expected));
    }

    // read

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        AINT64.read(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readEmptyBytes() throws IOException {
        // given
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AINT64.read(givenInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readInvalidBytes() throws IOException {
        // given
        byte[] given = new byte[]{0x00};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AINT64.read(givenInputStream);
    }

    @Test
    public void readEmpty() throws IOException {
        // given
        byte[] given = UINT32.ZERO.bytes();

        // expected
        List<INT64> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        List<INT64> actual = AINT64.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readOf() throws IOException {
        // given
        INT64 given = INT64.MAX_VALUE;

        // expected
        List<INT64> expected = new ArrayList<>();
        expected.add(given);

        // arrange
        byte[] givenBytes = ByteUtils.join(
                new UINT32(1).bytes(),
                given.bytes()
        );
        InputStream givenInputStream = new ByteArrayInputStream(givenBytes);

        // act
        List<INT64> actual = AINT64.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }
}
