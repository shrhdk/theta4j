package com.theta360.ptp.packet;

import com.theta360.ptp.data.GUID;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_COMMAND_ACK;
import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InitCommandAckPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE + GUID.SIZE + STR.MIN_SIZE + UINT32.SIZE];
    private static final UINT32 CONNECTION_NUMBER = new UINT32(0x00, 0x01, 0x02, 0x03);
    private static final GUID GUID_ = new GUID(
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    );
    private static final UINT32 PROTOCOL_VERSION = new UINT32(0x00, 0x01, 0x02, 0x03);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullConnectionNumber() {
        // act
        new InitCommandAckPacket(null, GUID_, "", PROTOCOL_VERSION);
    }

    @Test(expected = NullPointerException.class)
    public void withNullGUID() {
        // act
        new InitCommandAckPacket(CONNECTION_NUMBER, null, "", PROTOCOL_VERSION);
    }

    @Test(expected = NullPointerException.class)
    public void withNullName() {
        // act
        new InitCommandAckPacket(CONNECTION_NUMBER, GUID_, null, PROTOCOL_VERSION);
    }

    @Test(expected = NullPointerException.class)
    public void withNullProtocolVersion() {
        // act
        new InitCommandAckPacket(CONNECTION_NUMBER, GUID_, "", null);
    }

    // Constructor

    @Test
    public void constructAndGetWithEmptyName() {
        // given
        String givenName = "";

        // expected
        byte[] expectedPayload = ByteUtils.join(
                CONNECTION_NUMBER.bytes(),
                GUID_.bytes(),
                STR.toBytes(givenName),
                PROTOCOL_VERSION.bytes());

        // arrange
        InitCommandAckPacket packet = new InitCommandAckPacket(CONNECTION_NUMBER, GUID_, givenName, PROTOCOL_VERSION);

        // verify
        assertThat(packet.getType(), is(INIT_COMMAND_ACK));
        assertThat(packet.getConnectionNumber(), is(CONNECTION_NUMBER));
        assertThat(packet.getGUID(), is(GUID_));
        assertThat(packet.getName(), is(givenName));
        assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    @Test
    public void constructAndGet() {
        // given
        String givenName = "test";

        // expected
        byte[] expectedPayload = ByteUtils.join(
                CONNECTION_NUMBER.bytes(),
                GUID_.bytes(),
                STR.toBytes(givenName),
                PROTOCOL_VERSION.bytes());

        // arrange
        InitCommandAckPacket packet = new InitCommandAckPacket(CONNECTION_NUMBER, GUID_, givenName, PROTOCOL_VERSION);

        // verify
        assertThat(packet.getType(), is(INIT_COMMAND_ACK));
        assertThat(packet.getConnectionNumber(), is(CONNECTION_NUMBER));
        assertThat(packet.getGUID(), is(GUID_));
        assertThat(packet.getName(), is(givenName));
        assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        InitCommandAckPacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);

        // act
        InitCommandAckPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfTooShortPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // min length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_ACK, givenPayload);

        // act
        InitCommandAckPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidName() throws PacketException {
        // given
        byte[] invalidNameBytes = new byte[]{0x01};  // Not end with 0x00
        byte[] givenPayload = ByteUtils.join(
                CONNECTION_NUMBER.bytes(),
                GUID_.bytes(),
                invalidNameBytes,
                PROTOCOL_VERSION.bytes());

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_ACK, givenPayload);

        // act
        InitCommandAckPacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        String givenName = "test";
        byte[] givenPayload = ByteUtils.join(
                CONNECTION_NUMBER.bytes(),
                GUID_.bytes(),
                STR.toBytes(givenName),
                PROTOCOL_VERSION.bytes());

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_COMMAND_ACK, givenPayload);

        // act
        InitCommandAckPacket actual = InitCommandAckPacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(INIT_COMMAND_ACK));
        assertThat(actual.getConnectionNumber(), is(CONNECTION_NUMBER));
        assertThat(actual.getGUID(), is(GUID_));
        assertThat(actual.getName(), is(givenName));
        assertThat(actual.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(actual.getPayload(), is(givenPayload));
    }
}
