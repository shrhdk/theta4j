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

public class AUINT128Test {
    // valueOf

    @Test(expected = NullPointerException.class)
    public void valueOfNull() {
        // act
        AUINT128.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfEmptyBytes() {
        // act
        AUINT128.valueOf(new byte[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOfInvalidBytes() {
        // given
        byte[] given = new byte[]{0x00};

        // act
        AUINT128.valueOf(given);
    }

    @Test
    public void valueOfEmpty() {
        // given
        byte[] given = UINT32.ZERO.bytes();

        // expected
        List<UINT128> expected = new ArrayList<>();

        // act
        List<UINT128> actual = AUINT128.valueOf(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void valueOf() {
        // given
        UINT128 given = UINT128.MAX_VALUE;

        // expected
        List<UINT128> expected = new ArrayList<>();
        expected.add(given);

        // arrange
        byte[] givenBytes = ByteUtils.join(
                new UINT32(1).bytes(),
                given.bytes()
        );

        // act
        List<UINT128> actual = AUINT128.valueOf(givenBytes);

        // verify
        assertThat(actual, is(expected));
    }

    // read

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        AUINT128.read(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readEmptyBytes() throws IOException {
        // given
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AUINT128.read(givenInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readInvalidBytes() throws IOException {
        // given
        byte[] given = new byte[]{0x00};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AUINT128.read(givenInputStream);
    }

    @Test
    public void readEmpty() throws IOException {
        // given
        byte[] given = UINT32.ZERO.bytes();

        // expected
        List<UINT128> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        List<UINT128> actual = AUINT128.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readOf() throws IOException {
        // given
        UINT128 given = UINT128.MAX_VALUE;

        // expected
        List<UINT128> expected = new ArrayList<>();
        expected.add(given);

        // arrange
        byte[] givenBytes = ByteUtils.join(
                new UINT32(1).bytes(),
                given.bytes()
        );
        InputStream givenInputStream = new ByteArrayInputStream(givenBytes);

        // act
        List<UINT128> actual = AUINT128.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }
}
