package com.theta360.ptp.type;

import com.theta360.util.ByteUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AINT16Test {
    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        AINT16.read(null);
    }

    @Test(expected = EOFException.class)
    public void readEmptyBytes() throws IOException {
        // given
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AINT16.read(givenInputStream);
    }

    @Test(expected = EOFException.class)
    public void readInvalidBytes() throws IOException {
        // given
        byte[] given = new byte[]{0x00};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AINT16.read(givenInputStream);
    }

    // read

    @Test
    public void readEmpty() throws IOException {
        // given
        byte[] given = UINT32.ZERO.bytes();

        // expected
        List<INT16> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        List<INT16> actual = AINT16.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void read() throws IOException {
        // given
        INT16 given = INT16.MAX_VALUE;

        // expected
        List<INT16> expected = new ArrayList<>();
        expected.add(given);

        // arrange
        byte[] givenBytes = ByteUtils.join(
                new UINT32(1).bytes(),
                given.bytes()
        );
        InputStream givenInputStream = new ByteArrayInputStream(givenBytes);

        // act
        List<INT16> actual = AINT16.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }
}
