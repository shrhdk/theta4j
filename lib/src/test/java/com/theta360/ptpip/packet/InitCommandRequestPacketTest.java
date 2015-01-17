package com.theta360.ptpip.packet;

import com.theta360.ptp.data.GUID;
import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.PtpIpString;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.UnitTest;
import com.theta360.util.ByteUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_ACK;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class InitCommandRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[GUID.SIZE + STR.MIN_SIZE + UINT32.SIZE];
    private static final GUID GUID_ = new GUID(
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    );
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
                GUID_.bytes(),
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
        // given
        String givenName = "test";

        // expected
        byte[] expectedPayload = ByteUtils.join(
                GUID_.bytes(),
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

    @Test(expected = IOException.class)
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
        String givenName = "test";
        byte[] givenPayload = ByteUtils.join(
                GUID_.bytes(),
                PtpIpString.toBytes(givenName),
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
        assertThat(actual.getName(), is(givenName));
        assertThat(actual.getProtocolVersion(), is(PROTOCOL_VERSION));
        assertThat(actual.getPayload(), is(givenPayload));
    }
}
