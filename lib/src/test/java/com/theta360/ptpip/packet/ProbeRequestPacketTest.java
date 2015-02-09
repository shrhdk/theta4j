package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.PROBE_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        ProbeRequestPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, new byte[0]);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        ProbeRequestPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        ProbeRequestPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(PROBE_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        ProbeRequestPacket actual = ProbeRequestPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(PROBE_REQUEST));
        assertThat(actual.getPayload(), is(new byte[0]));
    }

    // hashCode

    @Test
    public void testHashCode() {
        // given
        ProbeRequestPacket packet1 = new ProbeRequestPacket();
        ProbeRequestPacket packet2 = new ProbeRequestPacket();

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        ProbeRequestPacket packet = new ProbeRequestPacket();

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        ProbeRequestPacket packet = new ProbeRequestPacket();

        // verify
        assertFalse(packet.equals("foo"));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        ProbeRequestPacket packet = new ProbeRequestPacket();

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        ProbeRequestPacket packet1 = new ProbeRequestPacket();
        ProbeRequestPacket packet2 = new ProbeRequestPacket();

        // verify
        assertTrue(packet1.equals(packet2));
    }
}
