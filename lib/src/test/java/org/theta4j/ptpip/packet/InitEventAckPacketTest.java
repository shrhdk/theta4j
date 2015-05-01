package org.theta4j.ptpip.packet;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.ptp.io.PtpInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_ACK;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;

@RunWith(Enclosed.class)
public class InitEventAckPacketTest {
    public static class Construct {
        @Test
        public void andGet() {
            // act
            InitEventAckPacket packet = new InitEventAckPacket();

            // verify
            assertThat(packet.getType(), is(INIT_EVENT_ACK));
            assertThat(packet.getPayload(), is(new byte[0]));
        }
    }

    public static class Read {
        @Test(expected = NullPointerException.class)
        public void nullValue() throws IOException {
            // act
            InitEventAckPacket.read(null);
        }

        @Test(expected = IOException.class)
        public void invalidType() throws IOException {
            // given
            PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, ArrayUtils.EMPTY_BYTE_ARRAY);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitEventAckPacket.read(givenInputStream);
        }

        @Test(expected = IOException.class)
        public void invalidLengthPayload() throws IOException {
            // given
            byte[] givenPayload = new byte[1]; // expected length + 1

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_EVENT_ACK, givenPayload);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitEventAckPacket.read(givenInputStream);
        }

        @Test
        public void normal() throws IOException {
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
    }

    public static class HashCode {
        @Test
        public void ofSameValues() {
            // given
            InitEventAckPacket packet1 = new InitEventAckPacket();
            InitEventAckPacket packet2 = new InitEventAckPacket();

            // verify
            assertThat(packet1.hashCode(), is(packet2.hashCode()));
        }
    }

    public static class NotEquals {
        @Test
        public void withNull() {
            // given
            InitEventAckPacket packet = new InitEventAckPacket();

            // verify
            assertFalse(packet.equals(null));
        }

        @Test
        public void withDifferentClass() {
            // given
            InitEventAckPacket packet = new InitEventAckPacket();

            // verify
            assertFalse(packet.equals("foo"));
        }
    }

    public static class Equals {
        @Test
        public void withSameInstances() {
            // given
            InitEventAckPacket packet = new InitEventAckPacket();

            // verify
            assertTrue(packet.equals(packet));
        }

        @Test
        public void withSameValues() {
            // given
            InitEventAckPacket packet1 = new InitEventAckPacket();
            InitEventAckPacket packet2 = new InitEventAckPacket();

            // verify
            assertTrue(packet1.equals(packet2));
        }
    }

    public static class ToString {
        @Test
        public void normal() {
            // given
            InitEventAckPacket packet = new InitEventAckPacket();

            // act
            String actual = packet.toString();

            // verify
            assertTrue(actual.contains(packet.getClass().getSimpleName()));
        }
    }
}
