package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_FAIL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InitFailPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE];
    private static final UINT32 REASON = new UINT32(0x00, 0x01, 0x02, 0x03);

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

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        InitFailPacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);

        // act
        InitFailPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfTooShortPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // expected length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_FAIL, givenPayload);

        // act
        InitFailPacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_FAIL, REASON.bytes());

        // act
        InitFailPacket actual = InitFailPacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(INIT_FAIL));
        assertThat(actual.getReason(), is(REASON));
        assertThat(actual.getPayload(), is(REASON.bytes()));
    }
}
