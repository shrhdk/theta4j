package org.theta4j.ptp.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.theta4j.ptp.type.*;
import org.theta4j.util.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    // write PtpInteger

    @Test(expected = NullPointerException.class)
    public void writeNullInteger() throws IOException {
        // act
        pos.write((PtpInteger) null);
    }

    @Test
    public void writeINT8() throws IOException {
        // given
        INT8 int8 = INT8.MAX_VALUE;

        // expected
        byte[] expected = int8.bytes();

        // act
        pos.write(int8);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT8() throws IOException {
        // given
        UINT8 uint8 = UINT8.MAX_VALUE;

        // expected
        byte[] expected = uint8.bytes();

        // act
        pos.write(uint8);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeINT16() throws IOException {
        // given
        INT16 int16 = INT16.MAX_VALUE;

        // expected
        byte[] expected = int16.bytes();

        // act
        pos.write(int16);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
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
    public void writeINT32() throws IOException {
        // given
        INT32 int32 = INT32.MAX_VALUE;

        // expected
        byte[] expected = int32.bytes();

        // act
        pos.write(int32);
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
    public void writeINT64() throws IOException {
        // given
        INT64 int64 = INT64.MAX_VALUE;

        // expected
        byte[] expected = int64.bytes();

        // act
        pos.write(int64);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT64() throws IOException {
        // given
        UINT64 uint64 = UINT64.MAX_VALUE;

        // expected
        byte[] expected = uint64.bytes();

        // act
        pos.write(uint64);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeINT128() throws IOException {
        // given
        INT128 int128 = INT128.MAX_VALUE;

        // expected
        byte[] expected = int128.bytes();

        // act
        pos.write(int128);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT128() throws IOException {
        // given
        UINT128 uint128 = UINT128.MAX_VALUE;

        // expected
        byte[] expected = uint128.bytes();

        // act
        pos.write(uint128);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    // write List<? extends PtpInteger>

    @Test(expected = NullPointerException.class)
    public void writeNullList() throws IOException {
        // act
        pos.write((List<? extends PtpInteger>) null);
    }

    @Test
    public void writeEmptyList() throws IOException {
        // given
        List<INT8> given = new ArrayList<>();

        // expected
        byte[] expected = new byte[]{};

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeINT8List() throws IOException {
        // given
        List<INT8> given = new ArrayList<>();
        given.add(INT8.MIN_VALUE);
        given.add(INT8.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                INT8.MIN_VALUE.bytes(),
                INT8.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT8List() throws IOException {
        // given
        List<UINT8> given = new ArrayList<>();
        given.add(UINT8.MIN_VALUE);
        given.add(UINT8.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                UINT8.MIN_VALUE.bytes(),
                UINT8.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeINT16List() throws IOException {
        // given
        List<INT16> given = new ArrayList<>();
        given.add(INT16.MIN_VALUE);
        given.add(INT16.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                INT16.MIN_VALUE.bytes(),
                INT16.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT16List() throws IOException {
        // given
        List<UINT16> given = new ArrayList<>();
        given.add(UINT16.MIN_VALUE);
        given.add(UINT16.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                UINT16.MIN_VALUE.bytes(),
                UINT16.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeINT32List() throws IOException {
        // given
        List<INT32> given = new ArrayList<>();
        given.add(INT32.MIN_VALUE);
        given.add(INT32.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                INT32.MIN_VALUE.bytes(),
                INT32.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT32List() throws IOException {
        // given
        List<UINT32> given = new ArrayList<>();
        given.add(UINT32.MIN_VALUE);
        given.add(UINT32.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                UINT32.MIN_VALUE.bytes(),
                UINT32.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeINT64List() throws IOException {
        // given
        List<INT64> given = new ArrayList<>();
        given.add(INT64.MIN_VALUE);
        given.add(INT64.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                INT64.MIN_VALUE.bytes(),
                INT64.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT64List() throws IOException {
        // given
        List<UINT64> given = new ArrayList<>();
        given.add(UINT64.MIN_VALUE);
        given.add(UINT64.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                UINT64.MIN_VALUE.bytes(),
                UINT64.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeINT128List() throws IOException {
        // given
        List<INT128> given = new ArrayList<>();
        given.add(INT128.MIN_VALUE);
        given.add(INT128.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                INT128.MIN_VALUE.bytes(),
                INT128.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void writeUINT128List() throws IOException {
        // given
        List<UINT128> given = new ArrayList<>();
        given.add(UINT128.MIN_VALUE);
        given.add(UINT128.MAX_VALUE);

        // expected
        byte[] expected = ArrayUtils.join(
                UINT128.MIN_VALUE.bytes(),
                UINT128.MAX_VALUE.bytes()
        );

        // act
        pos.write(given);
        byte[] actual = baos.toByteArray();

        // verify
        assertThat(actual, is(expected));
    }

    // write String

    @Test(expected = NullPointerException.class)
    public void writeNullString() throws IOException {
        // act
        pos.write((String) null);
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
