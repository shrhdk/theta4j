package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_FAIL;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class InitFailPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 REASON = new UINT32(0x00112233);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullReason() {
        // act
        new InitFailPacket(null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // act
        InitFailPacket packet = new InitFailPacket(REASON);

        // verify
        assertThat(packet.getType(), is(INIT_FAIL));
        assertThat(packet.getReason(), is(REASON));
        assertThat(packet.getPayload(), is(REASON.bytes()));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        InitFailPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitFailPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readTooShortPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // expected length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_FAIL, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitFailPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_FAIL, REASON.bytes());
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitFailPacket actual = InitFailPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(INIT_FAIL));
        assertThat(actual.getReason(), is(REASON));
        assertThat(actual.getPayload(), is(REASON.bytes()));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentReason() {
        // given
        InitFailPacket packet1 = new InitFailPacket(new UINT32(0));
        InitFailPacket packet2 = new InitFailPacket(new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        InitFailPacket packet1 = new InitFailPacket(REASON);
        InitFailPacket packet2 = new InitFailPacket(REASON);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        InitFailPacket packet = new InitFailPacket(REASON);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        InitFailPacket packet = new InitFailPacket(REASON);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithReason() {
        // given
        InitFailPacket packet1 = new InitFailPacket(new UINT32(0));
        InitFailPacket packet2 = new InitFailPacket(new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        InitFailPacket packet = new InitFailPacket(REASON);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        InitFailPacket packet1 = new InitFailPacket(REASON);
        InitFailPacket packet2 = new InitFailPacket(REASON);

        // verify
        assertTrue(packet1.equals(packet2));
    }
}
