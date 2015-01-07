package com.theta360.ptp;

import com.theta360.ptp.packet.PtpIpPacket;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PtpIpOutputStreamTest {
    @Test
    public void initCommandRequest() throws IOException {
        // given
        byte[] given = new byte[]{0x12, 0x34};

        // expected
        byte[] expected = new byte[]{
                0x0A, 0x00, 0x00, 0x00, // Length
                0x01, 0x00, 0x00, 0x00, // Type
                0x12, 0x34
        };

        // arrange
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PtpIpOutputStream out = new PtpIpOutputStream(baos);

        // Act
        out.write(new PtpIpPacket(PtpIpPacket.Type.INIT_COMMAND_REQUEST, given));

        // verify
        byte[] actual = baos.toByteArray();
        assertThat(actual, is(expected));
    }
}
