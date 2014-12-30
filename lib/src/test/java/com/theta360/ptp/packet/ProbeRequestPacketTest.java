package com.theta360.ptp.packet;

import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static com.theta360.ptp.packet.PtpIpPacket.Type.PROBE_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProbeRequestPacketTest {
    // Constructor

    @Test
    public void constructAndGet() {
        // act
        ProbeRequestPacket packet = new ProbeRequestPacket();

        // verify
        assertThat(packet.getType(), is(PROBE_REQUEST));
        assertThat(packet.getPayload(), is(new byte[0]));
    }

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        ProbeRequestPacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, new byte[0]);

        // act
        ProbeRequestPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidLengthPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_REQUEST, givenPayload);

        // act
        ProbeRequestPacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_REQUEST, givenPayload);

        // act
        ProbeRequestPacket actual = ProbeRequestPacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(PROBE_REQUEST));
        assertThat(actual.getPayload(), is(new byte[0]));
    }
}
