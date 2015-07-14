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
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_FAIL;

@RunWith(Enclosed.class)
public class InitFailPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 REASON = new UINT32(0x00112233);

    public static class Construct {
        @Test(expected = NullPointerException.class)
        public void withNullReason() {
            // act
            new InitFailPacket(null);
        }

        // Constructor

        @Test
        public void andGet() {
            // act
            InitFailPacket packet = new InitFailPacket(REASON);

            // verify
            assertThat(packet.getType(), is(INIT_FAIL));
            assertThat(packet.getReason(), is(REASON));
            assertThat(packet.getPayload(), is(REASON.bytes()));
        }
    }

    public static class Read {
        @Test(expected = NullPointerException.class)
        public void nullValue() throws IOException {
            // act
            InitFailPacket.read(null);
        }

        @Test(expected = IOException.class)
        public void invalidType() throws IOException {
            // given
            PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitFailPacket.read(givenInputStream);
        }

        @Test(expected = IOException.class)
        public void tooShortPayload() throws IOException {
            // given
            byte[] givenPayload = new byte[PAYLOAD.length - 1]; // expected length - 1

            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_FAIL, givenPayload);
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitFailPacket.read(givenInputStream);
        }

        @Test
        public void normal() throws IOException {
            // arrange
            byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(INIT_FAIL, REASON.bytes());
            PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

            // act
            InitFailPacket actual = InitFailPacket.read(givenInputStream);

            // verify
            assertThat(actual.getType(), is(INIT_FAIL));
            assertThat(actual.getReason(), is(REASON));
            assertThat(actual.getPayload(), is(REASON.bytes()));
        }
    }

    public static class HashCode {
        @Test
        public void ofDifferentReason() {
            // given
            InitFailPacket packet1 = new InitFailPacket(new UINT32(0));
            InitFailPacket packet2 = new InitFailPacket(new UINT32(1));

            // verify
            assertThat(packet1.hashCode(), not(packet2.hashCode()));
        }

        @Test
        public void ofSameValues() {
            // given
            InitFailPacket packet1 = new InitFailPacket(REASON);
            InitFailPacket packet2 = new InitFailPacket(REASON);

            // verify
            assertThat(packet1.hashCode(), is(packet2.hashCode()));
        }
    }

    public static class NotEquals {
        @Test
        public void withNull() {
            // given
            InitFailPacket packet = new InitFailPacket(REASON);

            // verify
            assertFalse(packet.equals(null));
        }

        @Test
        public void withDifferentClass() {
            // given
            InitFailPacket packet = new InitFailPacket(REASON);

            // verify
            assertFalse(packet.equals("foo"));
        }

        @Test
        public void withReason() {
            // given
            InitFailPacket packet1 = new InitFailPacket(new UINT32(0));
            InitFailPacket packet2 = new InitFailPacket(new UINT32(1));

            // verify
            assertFalse(packet1.equals(packet2));
        }
    }

    public static class Equals {
        @Test
        public void withSameInstances() {
            // given
            InitFailPacket packet = new InitFailPacket(REASON);

            // verify
            assertTrue(packet.equals(packet));
        }

        @Test
        public void withSameValues() {
            // given
            InitFailPacket packet1 = new InitFailPacket(REASON);
            InitFailPacket packet2 = new InitFailPacket(REASON);

            // verify
            assertTrue(packet1.equals(packet2));
        }
    }

    public static class ToString {
        @Test
        public void normal() {
            // given
            InitFailPacket packet = new InitFailPacket(REASON);

            // act
            String actual = packet.toString();

            // verify
            assertTrue(actual.contains(packet.getClass().getSimpleName()));
            assertTrue(actual.contains("reason"));
        }
    }
}
