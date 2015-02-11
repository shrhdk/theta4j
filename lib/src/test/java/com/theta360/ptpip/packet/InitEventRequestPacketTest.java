package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class InitEventRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 CONNECTION_NUMBER = new UINT32(0x00112233);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullConnectionNumber() {
        // act
        new InitEventRequestPacket(null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // act
        InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

        // verify
        assertThat(packet.getType(), is(INIT_EVENT_REQUEST));
        assertThat(packet.getConnectionNumber(), is(CONNECTION_NUMBER));
        assertThat(packet.getPayload(), is(CONNECTION_NUMBER.bytes()));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        InitEventRequestPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, new byte[0]);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitEventRequestPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readTooShortPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // min length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_EVENT_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitEventRequestPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_EVENT_REQUEST, CONNECTION_NUMBER.bytes());
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitEventRequestPacket actual = InitEventRequestPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(INIT_EVENT_REQUEST));
        assertThat(actual.getConnectionNumber(), is(CONNECTION_NUMBER));
        assertThat(actual.getPayload(), is(CONNECTION_NUMBER.bytes()));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentConnectionNumber() {
        // given
        InitEventRequestPacket packet1 = new InitEventRequestPacket(new UINT32(0));
        InitEventRequestPacket packet2 = new InitEventRequestPacket(new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        InitEventRequestPacket packet1 = new InitEventRequestPacket(CONNECTION_NUMBER);
        InitEventRequestPacket packet2 = new InitEventRequestPacket(CONNECTION_NUMBER);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithConnectionNumber() {
        // given
        InitEventRequestPacket packet1 = new InitEventRequestPacket(new UINT32(0));
        InitEventRequestPacket packet2 = new InitEventRequestPacket(new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        InitEventRequestPacket packet1 = new InitEventRequestPacket(CONNECTION_NUMBER);
        InitEventRequestPacket packet2 = new InitEventRequestPacket(CONNECTION_NUMBER);

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
        assertTrue(actual.contains("connectionNumber"));
    }
}
