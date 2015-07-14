/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;

@RunWith(Enclosed.class)
public class InitEventRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 CONNECTION_NUMBER = new UINT32(0x00112233);

    public static class Construct {
        @Test(expected = NullPointerException.class)
        public void withNullConnectionNumber() {
            // act
            new InitEventRequestPacket(null);
        }

        @Test
        public void andGet() {
            // act
            InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

            // verify
            assertThat(packet.getType(), is(INIT_EVENT_REQUEST));
            assertThat(packet.getConnectionNumber(), is(CONNECTION_NUMBER));
            assertThat(packet.getPayload(), is(CONNECTION_NUMBER.bytes()));
        }
    }

    public static class Read {
        @Test(expected = NullPointerException.class)
        public void nullValue() throws IOException {
            // act
            InitEventRequestPacket.read(null);
        }

        @Test(expected = IOException.class)
        public void invalidType() throws IOException {
            // given
            PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitEventRequestPacket.read(givenInputStream);
        }

        @Test(expected = IOException.class)
        public void tooShortPayload() throws IOException {
            // given
            byte[] givenPayload = new byte[PAYLOAD.length - 1]; // min length - 1

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_EVENT_REQUEST, givenPayload);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitEventRequestPacket.read(givenInputStream);
        }

        // read

        @Test
        public void normal() throws IOException {
            // given
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_EVENT_REQUEST, CONNECTION_NUMBER.bytes());
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitEventRequestPacket actual = InitEventRequestPacket.read(givenInputStream);

            // verify
            assertThat(actual.getType(), is(INIT_EVENT_REQUEST));
            assertThat(actual.getConnectionNumber(), is(CONNECTION_NUMBER));
            assertThat(actual.getPayload(), is(CONNECTION_NUMBER.bytes()));
        }
    }

    public static class HashCode {
        @Test
        public void ofDifferentConnectionNumber() {
            // given
            InitEventRequestPacket packet1 = new InitEventRequestPacket(new UINT32(0));
            InitEventRequestPacket packet2 = new InitEventRequestPacket(new UINT32(1));

            // verify
            assertThat(packet1.hashCode(), not(packet2.hashCode()));
        }

        @Test
        public void ofSameValues() {
            // given
            InitEventRequestPacket packet1 = new InitEventRequestPacket(CONNECTION_NUMBER);
            InitEventRequestPacket packet2 = new InitEventRequestPacket(CONNECTION_NUMBER);

            // verify
            assertThat(packet1.hashCode(), is(packet2.hashCode()));
        }
    }

    public static class NotEquals {
        @Test
        public void withNull() {
            // given
            InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

            // verify
            assertFalse(packet.equals(null));
        }

        @Test
        public void withDifferentClass() {
            // given
            InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

            // verify
            assertFalse(packet.equals("foo"));
        }

        @Test
        public void withConnectionNumber() {
            // given
            InitEventRequestPacket packet1 = new InitEventRequestPacket(new UINT32(0));
            InitEventRequestPacket packet2 = new InitEventRequestPacket(new UINT32(1));

            // verify
            assertFalse(packet1.equals(packet2));
        }
    }

    public static class Equals {
        @Test
        public void withSameInstances() {
            // given
            InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

            // verify
            assertTrue(packet.equals(packet));
        }

        @Test
        public void withSameValues() {
            // given
            InitEventRequestPacket packet1 = new InitEventRequestPacket(CONNECTION_NUMBER);
            InitEventRequestPacket packet2 = new InitEventRequestPacket(CONNECTION_NUMBER);

            // verify
            assertTrue(packet1.equals(packet2));
        }
    }

    public static class ToString {
        @Test
        public void normal() {
            // given
            InitEventRequestPacket packet = new InitEventRequestPacket(CONNECTION_NUMBER);

            // act
            String actual = packet.toString();

            // verify
            assertTrue(actual.contains(packet.getClass().getSimpleName()));
            assertTrue(actual.contains("connectionNumber"));
        }
    }
}
