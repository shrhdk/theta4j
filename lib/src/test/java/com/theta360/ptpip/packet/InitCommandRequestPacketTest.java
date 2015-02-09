package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.UUID;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_ACK;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class InitCommandRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[GUID.SIZE_IN_BYTES + STR.MIN_SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES];
    private static final UUID GUID_ = UUID.randomUUID();
    private static final String NAME = "test";
    private static final UINT32 PROTOCOL_VERSION = new UINT32(0x00112233);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullGUID() {
        // act
        new InitCommandRequestPacket(null, "", PROTOCOL_VERSION);
    }

    @Test(expected = NullPointerException.class)
    public void withNullName() {
        // act
        new InitCommandRequestPacket(GUID_, null, PROTOCOL_VERSION);
    }

    @Test(expected = NullPointerException.class)
    public void withNullProtocolVersion() {
        // act
        new InitCommandRequestPacket(GUID_, "", null);
    }

    @Test
    public void constructAndGetWithEmptyName() {
        // given
        String givenName = "";

        // expected
        byte[] expectedPayload = ByteUtils.join(
                GUID.toBytes(GUID_),
                PtpIpString.toBytes(givenName),
                PROTOCOL_VERSION.bytes()
        );

        // arrange
        InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, givenName, PROTOCOL_VERSION);

        // verify
        assertThat(packet.getType(), is(INIT_COMMAND_REQUEST));
        assertThat(packet.getGUID(), is(GUID_));
        assertThat(packet.getName(), is(givenName));
        assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // expected
        byte[] expectedPayload = ByteUtils.join(
                GUID.toBytes(GUID_),
                PtpIpString.toBytes(NAME),
                PROTOCOL_VERSION.bytes()
        );

        // arrange
        InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

        // verify
        assertThat(packet.getType(), is(INIT_COMMAND_REQUEST));
        assertThat(packet.getGUID(), is(GUID_));
        assertThat(packet.getName(), is(NAME));
        assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        InitCommandRequestPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_ACK;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitCommandRequestPacket.read(givenInputStream);
    }

    @Test(expected = EOFException.class)
    public void readTooShortPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // min length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitCommandRequestPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = ByteUtils.join(
                GUID.toBytes(GUID_),
                PtpIpString.toBytes(NAME),
                PROTOCOL_VERSION.bytes()
        );

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitCommandRequestPacket actual = InitCommandRequestPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(INIT_COMMAND_REQUEST));
        assertThat(actual.getGUID(), is(GUID_));
        assertThat(actual.getName(), is(NAME));
        assertThat(actual.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(actual.getPayload(), is(givenPayload));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentGUID() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentName() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, "foo", PROTOCOL_VERSION);
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, "bar", PROTOCOL_VERSION);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentProtocolVersion() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(0));
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithGUID() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithName() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, "foo", PROTOCOL_VERSION);
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, "bar", PROTOCOL_VERSION);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithProtocolVersion() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(0));
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);
        InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

        // verify
        assertTrue(packet1.equals(packet2));
    }
}
