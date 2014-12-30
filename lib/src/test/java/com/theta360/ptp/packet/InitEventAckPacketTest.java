package com.theta360.ptp.packet;

import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_EVENT_ACK;
import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InitEventAckPacketTest {
    // Constructor

    @Test
    public void constructAndGet() {
        // act
        InitEventAckPacket packet = new InitEventAckPacket();

        // verify
        assertThat(packet.getType(), is(INIT_EVENT_ACK));
        assertThat(packet.getPayload(), is(new byte[0]));
    }

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        InitEventAckPacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, new byte[0]);

        // act
        InitEventAckPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidLengthPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_EVENT_ACK, givenPayload);

        // act
        InitEventAckPacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_EVENT_ACK, givenPayload);

        // act
        InitEventAckPacket actual = InitEventAckPacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(INIT_EVENT_ACK));
        assertThat(actual.getPayload(), is(new byte[0]));
    }
}
