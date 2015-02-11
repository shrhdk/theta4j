package com.theta360.ptp.type;

import com.theta360.util.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AUINT32Test {
    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        AUINT32.read(null);
    }

    @Test(expected = EOFException.class)
    public void readEmptyBytes() throws IOException {
        // given
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AUINT32.read(givenInputStream);
    }

    @Test(expected = EOFException.class)
    public void readInvalidBytes() throws IOException {
        // given
        byte[] given = new byte[]{0x00};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        AUINT32.read(givenInputStream);
    }

    // read

    @Test
    public void readEmpty() throws IOException {
        // given
        byte[] given = UINT32.ZERO.bytes();

        // expected
        List<UINT32> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        List<UINT32> actual = AUINT32.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void read() throws IOException {
        // given
        UINT32 given = UINT32.MAX_VALUE;

        // expected
        List<UINT32> expected = new ArrayList<>();
        expected.add(given);

        // arrange
        byte[] givenBytes = ArrayUtils.join(
                new UINT32(1).bytes(),
                given.bytes()
        );
        InputStream givenInputStream = new ByteArrayInputStream(givenBytes);

        // act
        List<UINT32> actual = AUINT32.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }
}
