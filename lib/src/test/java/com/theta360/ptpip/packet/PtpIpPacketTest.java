package com.theta360.ptpip.packet;

import org.junit.Test;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PtpIpPacketTest {
    private static final PtpIpPacket.Type TYPE = INIT_COMMAND_REQUEST;
    private static final byte[] PAYLOAD = new byte[]{0x01, 0x02, 0x03};

    @Test
    public void bytes() {
        // given
        PtpIpPacket packet = new PtpIpPacket() {
            @Override
            public Type getType() {
                return TYPE;
            }

            @Override
            public byte[] getPayload() {
                return PAYLOAD;
            }
        };

        // expected
        byte[] expected = PtpIpPacketTestUtils.bytes(TYPE, PAYLOAD);

        // act
        byte[] actual = packet.bytes();

        // verify
        assertThat(actual, is(expected));
    }
}
