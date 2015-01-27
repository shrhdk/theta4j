package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_FAIL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class InitFailPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 REASON = new UINT32(0x00112233);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullReason() {
        // act
        new InitFailPacket(null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // act
        InitFailPacket packet = new InitFailPacket(REASON);

        // verify
        assertThat(packet.getType(), is(INIT_FAIL));
        assertThat(packet.getReason(), is(REASON));
        assertThat(packet.getPayload(), is(REASON.bytes()));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        InitFailPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_COMMAND_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitFailPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readTooShortPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1]; // expected length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_FAIL, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitFailPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(INIT_FAIL, REASON.bytes());
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        InitFailPacket actual = InitFailPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(INIT_FAIL));
        assertThat(actual.getReason(), is(REASON));
        assertThat(actual.getPayload(), is(REASON.bytes()));
    }
}
