package com.theta360.ptpip.packet;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GUIDTest {
    // toBytes

    @Test(expected = NullPointerException.class)
    public void nullToBytes() {
        // act
        GUID.toBytes(null);
    }

    @Test
    public void toBytes() {
        // given
        long msb = 0x0706050403020100L;
        long lsb = 0x0F0E0D0C0B0A0908L;
        UUID given = new UUID(msb, lsb);

        // expected
        byte[] expected = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
        };

        // act
        byte[] actual = GUID.toBytes(given);

        // verify
        assertThat(actual, is(expected));
    }

    // read

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        GUID.read(null);
    }

    @Test(expected = EOFException.class)
    public void readFromInsufficientStream() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E
        });

        // act
        GUID.read(given);
    }

    @Test
    public void read() throws IOException {
        // given
        InputStream given = new ByteArrayInputStream(new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
        });

        // expected
        long msb = 0x0706050403020100L;
        long lsb = 0x0F0E0D0C0B0A0908L;
        UUID expected = new UUID(msb, lsb);

        // act
        UUID actual = GUID.read(given);

        // verify
        assertThat(actual, is(expected));
    }
}
