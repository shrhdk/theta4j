package com.theta360.ptpip.packet;

import org.junit.Test;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PtpIpPacketTest {
    @Test(expected = NullPointerException.class)
    public void constructWithNullType() {
        new PtpIpPacket(null, new byte[0]);
    }

    @Test(expected = NullPointerException.class)
    public void constructWithNullPayload() {
        new PtpIpPacket(INIT_COMMAND_REQUEST, null);
    }

    @Test
    public void constructWithEmptyPayload() {
        PtpIpPacket.Type type = INIT_COMMAND_REQUEST;
        byte[] payload = new byte[0];
        PtpIpPacket packet = new PtpIpPacket(type, payload);

        byte[] actual = packet.bytes();
        byte[] expected = new byte[]{
                0x08, 0x00, 0x00, 0x00, // Length
                0x01, 0x00, 0x00, 0x00  // Type
        };

        assertThat(actual, is(expected));
    }

    @Test
    public void getType() {
        PtpIpPacket.Type given = INIT_COMMAND_REQUEST;
        PtpIpPacket.Type actual = new PtpIpPacket(given, new byte[0]).getType();

        assertThat(actual, is(given));
    }

    @Test
    public void getPayload() {
        byte[] given = new byte[]{0x01, 0x02, 0x03};
        byte[] actual = new PtpIpPacket(INIT_COMMAND_REQUEST, given).getPayload();

        assertThat(actual, is(given));
    }

    @Test
    public void getPayloadReturnsClone() {
        PtpIpPacket packet = new PtpIpPacket(INIT_COMMAND_REQUEST, new byte[]{0x01, 0x02, 0x03});

        byte[] actual1 = packet.getPayload();
        byte[] actual2 = packet.getPayload();

        assertTrue(actual1 != actual2);
        assertThat(actual1, is(actual2));
    }

    @Test
    public void bytes() {
        PtpIpPacket.Type type = INIT_COMMAND_REQUEST;
        byte[] payload = new byte[]{0x01, 0x02, 0x03};
        PtpIpPacket packet = new PtpIpPacket(type, payload);

        byte[] actual = packet.bytes();
        byte[] expected = new byte[]{
                0x0B, 0x00, 0x00, 0x00, // Length
                0x01, 0x00, 0x00, 0x00, // Type
                0x01, 0x02, 0x03        // Payload
        };

        assertThat(actual, is(expected));
    }
}
