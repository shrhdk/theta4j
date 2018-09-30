/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.io;

import org.junit.Test;
import org.theta4j.ptp.type.*;
import org.theta4j.util.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PtpInputStreamTest {
    // read PtpInteger

    @Test
    public void readINT8() throws IOException {
        // given
        INT8 given = INT8.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        INT8 actual = pis.readINT8();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readUINT8() throws IOException {
        // given
        UINT8 given = UINT8.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        UINT8 actual = pis.readUINT8();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readINT16() throws IOException {
        // given
        INT16 given = INT16.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        INT16 actual = pis.readINT16();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readUINT16() throws IOException {
        // given
        UINT16 given = UINT16.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        UINT16 actual = pis.readUINT16();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readINT32() throws IOException {
        // given
        INT32 given = INT32.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        INT32 actual = pis.readINT32();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readUINT32() throws IOException {
        // given
        UINT32 given = UINT32.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        UINT32 actual = pis.readUINT32();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readINT64() throws IOException {
        // given
        INT64 given = INT64.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        INT64 actual = pis.readINT64();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readUINT64() throws IOException {
        // given
        UINT64 given = UINT64.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        UINT64 actual = pis.readUINT64();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readINT128() throws IOException {
        // given
        INT128 given = INT128.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        INT128 actual = pis.readINT128();

        // verify
        assertThat(actual, is(given));
    }

    @Test
    public void readUINT128() throws IOException {
        // given
        UINT128 given = UINT128.MAX_VALUE;
        InputStream givenInputStream = new ByteArrayInputStream(given.bytes());

        // arrange
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        UINT128 actual = pis.readUINT128();

        // verify
        assertThat(actual, is(given));
    }

    // read AINT8

    @Test(expected = EOFException.class)
    public void readAINT8MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT8();
    }

    @Test(expected = EOFException.class)
    public void readAINT8MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT8();
    }

    @Test
    public void readEmptyAINT8() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<INT8> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT8> actual = pis.readAINT8();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAINT8() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                INT8.MAX_VALUE.bytes(),
                INT8.MAX_VALUE.bytes(),
                INT8.MAX_VALUE.bytes()
        );

        // expected
        List<INT8> expected = new ArrayList<>();
        expected.add(INT8.MAX_VALUE);
        expected.add(INT8.MAX_VALUE);
        expected.add(INT8.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT8> actual = pis.readAINT8();

        // verify
        assertThat(actual, is(expected));
    }

    // read AUINT8

    @Test(expected = EOFException.class)
    public void readAUINT8MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT8();
    }

    @Test(expected = EOFException.class)
    public void readAUINT8MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT8();
    }

    @Test
    public void readEmptyAUINT8() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<UINT8> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT8> actual = pis.readAUINT8();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAUINT8() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                UINT8.MAX_VALUE.bytes(),
                UINT8.MAX_VALUE.bytes(),
                UINT8.MAX_VALUE.bytes()
        );

        // expected
        List<UINT8> expected = new ArrayList<>();
        expected.add(UINT8.MAX_VALUE);
        expected.add(UINT8.MAX_VALUE);
        expected.add(UINT8.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT8> actual = pis.readAUINT8();

        // verify
        assertThat(actual, is(expected));
    }

    // read AINT16

    @Test(expected = EOFException.class)
    public void readAINT16MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT16();
    }

    @Test(expected = EOFException.class)
    public void readAINT16MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT16();
    }

    @Test(expected = EOFException.class)
    public void readAINT16InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT16();
    }

    @Test
    public void readEmptyAINT16() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<INT16> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT16> actual = pis.readAINT16();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAINT16() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                INT16.MAX_VALUE.bytes(),
                INT16.MAX_VALUE.bytes(),
                INT16.MAX_VALUE.bytes()
        );

        // expected
        List<INT16> expected = new ArrayList<>();
        expected.add(INT16.MAX_VALUE);
        expected.add(INT16.MAX_VALUE);
        expected.add(INT16.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT16> actual = pis.readAINT16();

        // verify
        assertThat(actual, is(expected));
    }

    // read AUINT16

    @Test(expected = EOFException.class)
    public void readAUINT16MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT16();
    }

    @Test(expected = EOFException.class)
    public void readAUINT16MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT16();
    }

    @Test(expected = EOFException.class)
    public void readAUINT16InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT16();
    }

    @Test
    public void readEmptyAUINT16() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<UINT16> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT16> actual = pis.readAUINT16();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAUINT16() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                UINT16.MAX_VALUE.bytes(),
                UINT16.MAX_VALUE.bytes(),
                UINT16.MAX_VALUE.bytes()
        );

        // expected
        List<UINT16> expected = new ArrayList<>();
        expected.add(UINT16.MAX_VALUE);
        expected.add(UINT16.MAX_VALUE);
        expected.add(UINT16.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT16> actual = pis.readAUINT16();

        // verify
        assertThat(actual, is(expected));
    }

    // read AINT32

    @Test(expected = EOFException.class)
    public void readAINT32MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT32();
    }

    @Test(expected = EOFException.class)
    public void readAINT32MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT32();
    }

    @Test(expected = EOFException.class)
    public void readAINT32InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT32();
    }

    @Test
    public void readEmptyAINT32() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<INT32> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT32> actual = pis.readAINT32();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAINT32() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                INT32.MAX_VALUE.bytes(),
                INT32.MAX_VALUE.bytes(),
                INT32.MAX_VALUE.bytes()
        );

        // expected
        List<INT32> expected = new ArrayList<>();
        expected.add(INT32.MAX_VALUE);
        expected.add(INT32.MAX_VALUE);
        expected.add(INT32.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT32> actual = pis.readAINT32();

        // verify
        assertThat(actual, is(expected));
    }

    // read AUINT32

    @Test(expected = EOFException.class)
    public void readAUINT32MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT32();
    }

    @Test(expected = EOFException.class)
    public void readAUINT32MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT32();
    }

    @Test(expected = EOFException.class)
    public void readAUINT32InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT32();
    }

    @Test
    public void readEmptyAUINT32() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<UINT32> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT32> actual = pis.readAUINT32();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAUINT32() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                UINT32.MAX_VALUE.bytes(),
                UINT32.MAX_VALUE.bytes(),
                UINT32.MAX_VALUE.bytes()
        );

        // expected
        List<UINT32> expected = new ArrayList<>();
        expected.add(UINT32.MAX_VALUE);
        expected.add(UINT32.MAX_VALUE);
        expected.add(UINT32.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT32> actual = pis.readAUINT32();

        // verify
        assertThat(actual, is(expected));
    }

    // read AINT64

    @Test(expected = EOFException.class)
    public void readAINT64MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT64();
    }

    @Test(expected = EOFException.class)
    public void readAINT64MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT64();
    }

    @Test(expected = EOFException.class)
    public void readAINT64InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT64();
    }

    @Test
    public void readEmptyAINT64() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<INT64> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT64> actual = pis.readAINT64();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAINT64() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                INT64.MAX_VALUE.bytes(),
                INT64.MAX_VALUE.bytes(),
                INT64.MAX_VALUE.bytes()
        );

        // expected
        List<INT64> expected = new ArrayList<>();
        expected.add(INT64.MAX_VALUE);
        expected.add(INT64.MAX_VALUE);
        expected.add(INT64.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT64> actual = pis.readAINT64();

        // verify
        assertThat(actual, is(expected));
    }

    // read AUINT64

    @Test(expected = EOFException.class)
    public void readAUINT64MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT64();
    }

    @Test(expected = EOFException.class)
    public void readAUINT64MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT64();
    }

    @Test(expected = EOFException.class)
    public void readAUINT64InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT64();
    }

    @Test
    public void readEmptyAUINT64() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<UINT64> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT64> actual = pis.readAUINT64();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAUINT64() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                UINT64.MAX_VALUE.bytes(),
                UINT64.MAX_VALUE.bytes(),
                UINT64.MAX_VALUE.bytes()
        );

        // expected
        List<UINT64> expected = new ArrayList<>();
        expected.add(UINT64.MAX_VALUE);
        expected.add(UINT64.MAX_VALUE);
        expected.add(UINT64.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT64> actual = pis.readAUINT64();

        // verify
        assertThat(actual, is(expected));
    }

    // read AINT128

    @Test(expected = EOFException.class)
    public void readAINT128MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT128();
    }

    @Test(expected = EOFException.class)
    public void readAINT128MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT128();
    }

    @Test(expected = EOFException.class)
    public void readAINT128InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAINT128();
    }

    @Test
    public void readEmptyAINT128() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<INT128> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT128> actual = pis.readAINT128();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAINT128() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                INT128.MAX_VALUE.bytes(),
                INT128.MAX_VALUE.bytes(),
                INT128.MAX_VALUE.bytes()
        );

        // expected
        List<INT128> expected = new ArrayList<>();
        expected.add(INT128.MAX_VALUE);
        expected.add(INT128.MAX_VALUE);
        expected.add(INT128.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<INT128> actual = pis.readAINT128();

        // verify
        assertThat(actual, is(expected));
    }

    // read AUINT128

    @Test(expected = EOFException.class)
    public void readAUINT128MissingHeader() throws IOException {
        // given (missing array header)
        byte[] given = new byte[]{};

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT128();
    }

    @Test(expected = EOFException.class)
    public void readAUINT128MissingBody() throws IOException {
        // given (missing array body)
        byte[] given = new UINT32(1).bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT128();
    }

    @Test(expected = EOFException.class)
    public void readAUINT128InvalidBody() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()  // Invalid Type Element
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAUINT128();
    }

    @Test
    public void readEmptyAUINT128() throws IOException {
        // given
        byte[] given = new UINT32(0).bytes();

        // expected
        List<UINT128> expected = new ArrayList<>();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT128> actual = pis.readAUINT128();

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAUINT128() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(3).bytes(),
                UINT128.MAX_VALUE.bytes(),
                UINT128.MAX_VALUE.bytes(),
                UINT128.MAX_VALUE.bytes()
        );

        // expected
        List<UINT128> expected = new ArrayList<>();
        expected.add(UINT128.MAX_VALUE);
        expected.add(UINT128.MAX_VALUE);
        expected.add(UINT128.MAX_VALUE);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        List<UINT128> actual = pis.readAUINT128();

        // verify
        assertThat(actual, is(expected));
    }

    // read String

    @Test
    public void readString() throws IOException {
        // given
        String given = "test";

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(STR.toBytes(given));
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        String actual = pis.readString();

        // verify
        assertThat(actual, is(given));
    }

    // readAs(null)

    @Test(expected = NullPointerException.class)
    public void readAsNull() throws IOException {
        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(new byte[]{});
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        pis.readAs(null);
    }

    // readAs(PtpInteger)

    @Test
    public void readAsINT8() throws IOException {
        // given
        byte[] given = INT8.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        INT8 expected = INT8.MAX_VALUE;

        // act
        INT8 actual = (INT8) pis.readAs(DataType.INT8);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsUINT8() throws IOException {
        // given
        byte[] given = UINT8.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        UINT8 expected = UINT8.MAX_VALUE;

        // act
        UINT8 actual = (UINT8) pis.readAs(DataType.UINT8);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsINT16() throws IOException {
        // given
        byte[] given = INT16.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        INT16 expected = INT16.MAX_VALUE;

        // act
        INT16 actual = (INT16) pis.readAs(DataType.INT16);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsUINT16() throws IOException {
        // given
        byte[] given = UINT16.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        UINT16 expected = UINT16.MAX_VALUE;

        // act
        UINT16 actual = (UINT16) pis.readAs(DataType.UINT16);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsINT32() throws IOException {
        // given
        byte[] given = INT32.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        INT32 expected = INT32.MAX_VALUE;

        // act
        INT32 actual = (INT32) pis.readAs(DataType.INT32);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsUINT32() throws IOException {
        // given
        byte[] given = UINT32.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        UINT32 expected = UINT32.MAX_VALUE;

        // act
        UINT32 actual = (UINT32) pis.readAs(DataType.UINT32);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsINT64() throws IOException {
        // given
        byte[] given = INT64.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        INT64 expected = INT64.MAX_VALUE;

        // act
        INT64 actual = (INT64) pis.readAs(DataType.INT64);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsUINT64() throws IOException {
        // given
        byte[] given = UINT64.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        UINT64 expected = UINT64.MAX_VALUE;

        // act
        UINT64 actual = (UINT64) pis.readAs(DataType.UINT64);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsINT128() throws IOException {
        // given
        byte[] given = INT128.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        INT128 expected = INT128.MAX_VALUE;

        // act
        INT128 actual = (INT128) pis.readAs(DataType.INT128);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void readAsUINT128() throws IOException {
        // given
        byte[] given = UINT128.MAX_VALUE.bytes();

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        UINT128 expected = UINT128.MAX_VALUE;

        // act
        UINT128 actual = (UINT128) pis.readAs(DataType.UINT128);

        // verify
        assertThat(actual, is(expected));
    }

    // readAs(List of PtpInteger)

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAINT8() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT8.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<INT8> expected = new ArrayList<>();
        expected.add(INT8.MAX_VALUE);

        // act
        List<INT8> actual = (List<INT8>) pis.readAs(DataType.AINT8);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAUINT8() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                UINT8.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<UINT8> expected = new ArrayList<>();
        expected.add(UINT8.MAX_VALUE);

        // act
        List<UINT8> actual = (List<UINT8>) pis.readAs(DataType.AUINT8);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAINT16() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT16.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<INT16> expected = new ArrayList<>();
        expected.add(INT16.MAX_VALUE);

        // act
        List<INT16> actual = (List<INT16>) pis.readAs(DataType.AINT16);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAUINT16() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                UINT16.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<UINT16> expected = new ArrayList<>();
        expected.add(UINT16.MAX_VALUE);

        // act
        List<UINT16> actual = (List<UINT16>) pis.readAs(DataType.AUINT16);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAINT32() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT32.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<INT32> expected = new ArrayList<>();
        expected.add(INT32.MAX_VALUE);

        // act
        List<INT32> actual = (List<INT32>) pis.readAs(DataType.AINT32);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAUINT32() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                UINT32.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<UINT32> expected = new ArrayList<>();
        expected.add(UINT32.MAX_VALUE);

        // act
        List<UINT32> actual = (List<UINT32>) pis.readAs(DataType.AUINT32);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAINT64() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT64.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<INT64> expected = new ArrayList<>();
        expected.add(INT64.MAX_VALUE);

        // act
        List<INT64> actual = (List<INT64>) pis.readAs(DataType.AINT64);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAUINT64() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                UINT64.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<UINT64> expected = new ArrayList<>();
        expected.add(UINT64.MAX_VALUE);

        // act
        List<UINT64> actual = (List<UINT64>) pis.readAs(DataType.AUINT64);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAINT128() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                INT128.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<INT128> expected = new ArrayList<>();
        expected.add(INT128.MAX_VALUE);

        // act
        List<INT128> actual = (List<INT128>) pis.readAs(DataType.AINT128);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void readAsAUINT128() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                new UINT32(1).bytes(),
                UINT128.MAX_VALUE.bytes()
        );

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // expected
        List<UINT128> expected = new ArrayList<>();
        expected.add(UINT128.MAX_VALUE);

        // act
        List<UINT128> actual = (List<UINT128>) pis.readAs(DataType.AUINT128);

        // verify
        assertThat(actual, is(expected));
    }

    // readAs(STR)

    @Test
    public void readAsString() throws IOException {
        // given
        String given = "test";

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(STR.toBytes(given));
        PtpInputStream pis = new PtpInputStream(givenInputStream);

        // act
        String actual = (String) pis.readAs(DataType.STR);

        // verify
        assertThat(actual, is(given));
    }
}
