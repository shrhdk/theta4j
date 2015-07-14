/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.STR;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_ACK;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;

@RunWith(Enclosed.class)
public class InitCommandRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[GUID.SIZE_IN_BYTES + STR.MIN_SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES];
    private static final UUID GUID_ = UUID.randomUUID();
    private static final String NAME = "test";
    private static final UINT32 PROTOCOL_VERSION = new UINT32(0x00112233);

    public static class Construct {
        @Test(expected = NullPointerException.class)
        public void withNullGUID() {
            // act
            new InitCommandRequestPacket(null, "", PROTOCOL_VERSION);
        }

        @Test(expected = NullPointerException.class)
        public void withNullName() {
            // act
            new InitCommandRequestPacket(GUID_, null, PROTOCOL_VERSION);
        }

        @Test(expected = NullPointerException.class)
        public void withNullProtocolVersion() {
            // act
            new InitCommandRequestPacket(GUID_, "", null);
        }

        @Test
        public void withEmptyNameAndGet() {
            // given
            String givenName = "";

            // expected
            byte[] expectedPayload = ArrayUtils.join(
                    GUID.toBytes(GUID_),
                    PtpIpString.toBytes(givenName),
                    PROTOCOL_VERSION.bytes()
            );

            // arrange
            InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, givenName, PROTOCOL_VERSION);

            // verify
            assertThat(packet.getType(), is(INIT_COMMAND_REQUEST));
            assertThat(packet.getGUID(), is(GUID_));
            assertThat(packet.getName(), is(givenName));
            assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
            assertThat(packet.getPayload(), is(expectedPayload));
        }

        @Test
        public void andGet() {
            // expected
            byte[] expectedPayload = ArrayUtils.join(
                    GUID.toBytes(GUID_),
                    PtpIpString.toBytes(NAME),
                    PROTOCOL_VERSION.bytes()
            );

            // arrange
            InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

            // verify
            assertThat(packet.getType(), is(INIT_COMMAND_REQUEST));
            assertThat(packet.getGUID(), is(GUID_));
            assertThat(packet.getName(), is(NAME));
            assertThat(packet.getProtocolVersion(), is(PROTOCOL_VERSION));
            assertThat(packet.getPayload(), is(expectedPayload));
        }
    }

    public static class Read {
        @Test(expected = NullPointerException.class)
        public void nullValue() throws IOException {
            // act
            InitCommandRequestPacket.read(null);
        }

        @Test(expected = IOException.class)
        public void invalidType() throws IOException {
            // given
            PtpIpPacket.Type invalidType = INIT_COMMAND_ACK;

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitCommandRequestPacket.read(givenInputStream);
        }

        @Test(expected = EOFException.class)
        public void tooShortPayload() throws IOException {
            // given
            byte[] givenPayload = new byte[PAYLOAD.length - 1]; // min length - 1

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_COMMAND_REQUEST, givenPayload);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitCommandRequestPacket.read(givenInputStream);
        }

        // read

        @Test
        public void normal() throws IOException {
            // given
            byte[] givenPayload = ArrayUtils.join(
                    GUID.toBytes(GUID_),
                    PtpIpString.toBytes(NAME),
                    PROTOCOL_VERSION.bytes()
            );

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_COMMAND_REQUEST, givenPayload);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitCommandRequestPacket actual = InitCommandRequestPacket.read(givenInputStream);

            // verify
            assertThat(actual.getType(), is(INIT_COMMAND_REQUEST));
            assertThat(actual.getGUID(), is(GUID_));
            assertThat(actual.getName(), is(NAME));
            assertThat(actual.getProtocolVersion(), is(PROTOCOL_VERSION));
            assertThat(actual.getPayload(), is(givenPayload));
        }
    }

    public static class HashCode {
        @Test
        public void ofDifferentGUID() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);

            // verify
            assertThat(packet1.hashCode(), not(packet2.hashCode()));
        }

        @Test
        public void ofDifferentName() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, "foo", PROTOCOL_VERSION);
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, "bar", PROTOCOL_VERSION);

            // verify
            assertThat(packet1.hashCode(), not(packet2.hashCode()));
        }

        @Test
        public void ofDifferentProtocolVersion() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(0));
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(1));

            // verify
            assertThat(packet1.hashCode(), not(packet2.hashCode()));
        }

        @Test
        public void ofSameValues() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

            // verify
            assertThat(packet1.hashCode(), is(packet2.hashCode()));
        }
    }

    public static class NotEquals {
        @Test
        public void withNull() {
            // given
            InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

            // verify
            assertFalse(packet.equals(null));
        }

        @Test
        public void withDifferentClass() {
            // given
            InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

            // verify
            assertFalse(packet.equals("foo"));
        }

        @Test
        public void withGUID() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(UUID.randomUUID(), NAME, PROTOCOL_VERSION);

            // verify
            assertFalse(packet1.equals(packet2));
        }

        @Test
        public void withName() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, "foo", PROTOCOL_VERSION);
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, "bar", PROTOCOL_VERSION);

            // verify
            assertFalse(packet1.equals(packet2));
        }

        @Test
        public void withProtocolVersion() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(0));
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, new UINT32(1));

            // verify
            assertFalse(packet1.equals(packet2));
        }
    }

    public static class Equals {
        @Test
        public void withSameInstances() {
            // given
            InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

            // verify
            assertTrue(packet.equals(packet));
        }

        @Test
        public void withSameValues() {
            // given
            InitCommandRequestPacket packet1 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);
            InitCommandRequestPacket packet2 = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

            // verify
            assertTrue(packet1.equals(packet2));
        }
    }

    public static class ToString {
        @Test
        public void normal() {
            // given
            InitCommandRequestPacket packet = new InitCommandRequestPacket(GUID_, NAME, PROTOCOL_VERSION);

            // act
            String actual = packet.toString();

            // verify
            assertTrue(actual.contains(packet.getClass().getSimpleName()));
            assertTrue(actual.contains("guid"));
            assertTrue(actual.contains("name"));
            assertTrue(actual.contains("protocolVersion"));
        }
    }
}
