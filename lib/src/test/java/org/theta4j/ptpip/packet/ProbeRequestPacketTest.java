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
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.PROBE_REQUEST;

@RunWith(Enclosed.class)
public class ProbeRequestPacketTest {
    public static class Construct {
        @Test
        public void andGet() {
            // act
            ProbeRequestPacket packet = new ProbeRequestPacket();

            // verify
            assertThat(packet.getType(), is(PROBE_REQUEST));
            assertThat(packet.getPayload(), is(new byte[0]));
        }
    }

    public static class Read {
        @Test(expected = NullPointerException.class)
        public void nullValue() throws IOException {
            // act
            ProbeRequestPacket.read(null);
        }

        @Test(expected = IOException.class)
        public void invalidType() throws IOException {
            // given
            PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, ArrayUtils.EMPTY_BYTE_ARRAY);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            ProbeRequestPacket.read(givenInputStream);
        }

        @Test(expected = IOException.class)
        public void invalidLengthPayload() throws IOException {
            // given
            byte[] givenPayload = new byte[1]; // expected length + 1

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PROBE_REQUEST, givenPayload);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            ProbeRequestPacket.read(givenInputStream);
        }

        @Test
        public void normal() throws IOException {
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
    }

    public static class HashCode {
        @Test
        public void ofSameValues() {
            // given
            ProbeRequestPacket packet1 = new ProbeRequestPacket();
            ProbeRequestPacket packet2 = new ProbeRequestPacket();

            // verify
            assertThat(packet1.hashCode(), is(packet2.hashCode()));
        }
    }

    public static class NotEquals {
        @Test
        public void withNull() {
            // given
            ProbeRequestPacket packet = new ProbeRequestPacket();

            // verify
            assertFalse(packet.equals(null));
        }

        @Test
        public void withDifferentClass() {
            // given
            ProbeRequestPacket packet = new ProbeRequestPacket();

            // verify
            assertFalse(packet.equals("foo"));
        }
    }

    public static class Equals {
        @Test
        public void withSameInstances() {
            // given
            ProbeRequestPacket packet = new ProbeRequestPacket();

            // verify
            assertTrue(packet.equals(packet));
        }

        @Test
        public void withSameValues() {
            // given
            ProbeRequestPacket packet1 = new ProbeRequestPacket();
            ProbeRequestPacket packet2 = new ProbeRequestPacket();

            // verify
            assertTrue(packet1.equals(packet2));
        }
    }

    public static class ToString {
        @Test
        public void normal() {
            // given
            ProbeRequestPacket packet = new ProbeRequestPacket();

            // act
            String actual = packet.toString();

            // verify
            assertTrue(actual.contains(packet.getClass().getSimpleName()));
        }
    }
}
