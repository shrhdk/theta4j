package org.theta4j.ptpip.packet;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.theta4j.ptp.io.PtpInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.PROBE_REQUEST;

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
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, ArrayUtils.EMPTY_BYTE_ARRAY);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        ProbeRequestPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PROBE_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        ProbeRequestPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PROBE_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

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

    // toString

    @Test
    public void testToString() {
        // given
        ProbeRequestPacket packet = new ProbeRequestPacket();

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
    }
}
