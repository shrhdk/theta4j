package com.theta360.ptp.packet;

import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static com.theta360.ptp.packet.PtpIpPacket.Type.PROBE_RESPONSE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class ProbeResponsePacketTest {
    // Constructor

    @Test
    public void constructAndGet() {
        // act
        ProbeResponsePacket packet = new ProbeResponsePacket();

        // verify
        assertThat(packet.getType(), is(PROBE_RESPONSE));
        assertThat(packet.getPayload(), is(new byte[0]));
    }

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        ProbeResponsePacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, new byte[0]);

        // act
        ProbeResponsePacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidLengthPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_RESPONSE, givenPayload);

        // act
        ProbeResponsePacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_RESPONSE, givenPayload);

        // act
        ProbeResponsePacket actual = ProbeResponsePacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(PROBE_RESPONSE));
        assertThat(actual.getPayload(), is(new byte[0]));
    }
}
