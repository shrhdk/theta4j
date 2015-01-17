package com.theta360.ptp.io;

import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.test.categories.UnitTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
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
        UINT16 uint16 = UINT16.MAX_VALUE;

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
        UINT32 uint32 = UINT32.MAX_VALUE;

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
}
