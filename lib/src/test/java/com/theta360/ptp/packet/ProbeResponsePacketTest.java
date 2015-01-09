package com.theta360.ptp.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        ProbeResponsePacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, new byte[0]);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        ProbeResponsePacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_RESPONSE, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        ProbeResponsePacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_RESPONSE, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        ProbeResponsePacket actual = ProbeResponsePacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(PROBE_RESPONSE));
        assertThat(actual.getPayload(), is(new byte[0]));
    }
}
