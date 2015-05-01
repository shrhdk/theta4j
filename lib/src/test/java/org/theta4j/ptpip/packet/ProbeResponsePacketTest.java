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
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.PROBE_RESPONSE;

@RunWith(Enclosed.class)
public class ProbeResponsePacketTest {
    public static class Construct {
        @Test
        public void andGet() {
            // act
            ProbeResponsePacket packet = new ProbeResponsePacket();

            // verify
            assertThat(packet.getType(), is(PROBE_RESPONSE));
            assertThat(packet.getPayload(), is(new byte[0]));
        }
    }

    public static class Read {
        @Test(expected = NullPointerException.class)
        public void nullValue() throws IOException {
            // act
            ProbeResponsePacket.read(null);
        }

        @Test(expected = IOException.class)
        public void invalidType() throws IOException {
            // given
            PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, ArrayUtils.EMPTY_BYTE_ARRAY);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            ProbeResponsePacket.read(givenInputStream);
        }

        @Test(expected = IOException.class)
        public void invalidLengthPayload() throws IOException {
            // given
            byte[] givenPayload = new byte[1]; // expected length + 1

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PROBE_RESPONSE, givenPayload);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            ProbeResponsePacket.read(givenInputStream);
        }

        @Test
        public void normal() throws IOException {
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
    }

    public static class HashCode {
        @Test
        public void ofSameValues() {
            // given
            ProbeResponsePacket packet1 = new ProbeResponsePacket();
            ProbeResponsePacket packet2 = new ProbeResponsePacket();

            // verify
            assertThat(packet1.hashCode(), is(packet2.hashCode()));
        }
    }

    public static class NotEquals {
        @Test
        public void withNull() {
            // given
            ProbeResponsePacket packet = new ProbeResponsePacket();

            // verify
            assertFalse(packet.equals(null));
        }

        @Test
        public void withDifferentClass() {
            // given
            ProbeResponsePacket packet = new ProbeResponsePacket();

            // verify
            assertFalse(packet.equals("foo"));
        }
    }

    public static class Equals {
        @Test
        public void withSameInstances() {
            // given
            ProbeResponsePacket packet = new ProbeResponsePacket();

            // verify
            assertTrue(packet.equals(packet));
        }

        @Test
        public void withSameValues() {
            // given
            ProbeResponsePacket packet1 = new ProbeResponsePacket();
            ProbeResponsePacket packet2 = new ProbeResponsePacket();

            // verify
            assertTrue(packet1.equals(packet2));
        }
    }

    public static class ToString {
        @Test
        public void normal() {
            // given
            ProbeResponsePacket packet = new ProbeResponsePacket();

            // act
            String actual = packet.toString();

            // verify
            assertTrue(actual.contains(packet.getClass().getSimpleName()));
        }
    }
}
