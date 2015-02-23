package org.theta4j.ptpip.packet;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.theta4j.ptp.io.PtpInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.PROBE_RESPONSE;

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
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, ArrayUtils.EMPTY_BYTE_ARRAY);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        ProbeResponsePacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PROBE_RESPONSE, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        ProbeResponsePacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PROBE_RESPONSE, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        ProbeResponsePacket actual = ProbeResponsePacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(PROBE_RESPONSE));
        assertThat(actual.getPayload(), is(new byte[0]));
    }

    // hashCode

    @Test
    public void testHashCode() {
        // given
        ProbeResponsePacket packet1 = new ProbeResponsePacket();
        ProbeResponsePacket packet2 = new ProbeResponsePacket();

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        ProbeResponsePacket packet = new ProbeResponsePacket();

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        ProbeResponsePacket packet = new ProbeResponsePacket();

        // verify
        assertFalse(packet.equals("foo"));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        ProbeResponsePacket packet = new ProbeResponsePacket();

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        ProbeResponsePacket packet1 = new ProbeResponsePacket();
        ProbeResponsePacket packet2 = new ProbeResponsePacket();

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        ProbeResponsePacket packet = new ProbeResponsePacket();

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
    }
}
