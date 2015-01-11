package com.theta360.ptp.io;

import com.theta360.ptp.type.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PtpOutputStreamTest {
    private ByteArrayOutputStream baos;
    private PtpOutputStream pos;

    @Before
    public void setUp() {
        baos = new ByteArrayOutputStream();
        pos = new PtpOutputStream(baos);
    }

    @After
    public void cleanUp() throws IOException {
        pos.close();
    }

    @Test
    public void writeUINT16() throws IOException {
        // given
        UINT16 uint16 = new UINT16(UINT16.MAX_VALUE);

        // expected
        byte[] expected = uint16.bytes();

        // act
        pos.write(uint16);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT32() throws IOException {
        // given
        UINT32 uint32 = new UINT32(UINT32.MAX_VALUE);

        // expected
        byte[] expected = uint32.bytes();

        // act
        pos.write(uint32);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT64() throws IOException {
        // given
        UINT64 uint64 = UINT64.ZERO;

        // expected
        byte[] expected = uint64.bytes();

        // act
        pos.write(uint64);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeString() throws IOException {
        // given
        String given = "test";

        // expected
        byte[] expected = STR.toBytes(given);

        // act
        pos.write("test");
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writePtpIpString() throws IOException {
        // given
        String given = "test";

        // expected
        byte[] expected = PtpIpString.toBytes(given);

        // act
        pos.writePtpIpString("test");
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }
}
