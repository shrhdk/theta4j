package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_ACK;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        InitEventAckPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, ArrayUtils.EMPTY_BYTE_ARRAY);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        InitEventAckPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[1]; // expected length + 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_EVENT_ACK, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        InitEventAckPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = new byte[0];

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_EVENT_ACK, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        InitEventAckPacket actual = InitEventAckPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(INIT_EVENT_ACK));
        assertThat(actual.getPayload(), is(new byte[0]));
    }

    // hashCode

    @Test
    public void testHashCode() {
        // given
        InitEventAckPacket packet1 = new InitEventAckPacket();
        InitEventAckPacket packet2 = new InitEventAckPacket();

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        InitEventAckPacket packet = new InitEventAckPacket();

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        InitEventAckPacket packet = new InitEventAckPacket();

        // verify
        assertFalse(packet.equals("foo"));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        InitEventAckPacket packet = new InitEventAckPacket();

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        InitEventAckPacket packet1 = new InitEventAckPacket();
        InitEventAckPacket packet2 = new InitEventAckPacket();

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        InitEventAckPacket packet = new InitEventAckPacket();

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
    }
}
