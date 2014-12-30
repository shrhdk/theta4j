package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InitEventRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE];
    private static final UINT32 CONNECTION_NUMBER = new UINT32(0x00, 0x01, 0x02, 0x03);

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

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        InitEventRequestPacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, new byte[0]);

        // act
        InitEventRequestPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfTooShortPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // min length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_EVENT_REQUEST, givenPayload);

        // act
        InitEventRequestPacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_EVENT_REQUEST, CONNECTION_NUMBER.bytes());

        // act
        InitEventRequestPacket actual = InitEventRequestPacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(INIT_EVENT_REQUEST));
        assertThat(actual.getConnectionNumber(), is(CONNECTION_NUMBER));
        assertThat(actual.getPayload(), is(CONNECTION_NUMBER.bytes()));
    }
}
