package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.test.categories.UnitTest;
import com.theta360.util.ByteUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.START_DATA;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class StartDataPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE + UINT64.SIZE];
    private static final UINT32 TRANSACTION_ID = new UINT32(0);
    private static final UINT64 TOTAL_DATA_LENGTH = UINT64.ZERO;

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new StartDataPacket(null, TOTAL_DATA_LENGTH);
    }

    @Test(expected = NullPointerException.class)
    public void withNullTotalDataLength() {
        // act
        new StartDataPacket(TRANSACTION_ID, null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // expected
        byte[] expectedPayload = ByteUtils.join(
                TRANSACTION_ID.bytes(),
                TOTAL_DATA_LENGTH.bytes()
        );

        // act
        StartDataPacket packet = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // verify
        assertThat(packet.getType(), is(START_DATA));
        assertThat(packet.getTransactionID(), is(TRANSACTION_ID));
        assertThat(packet.getTotalDataLength(), is(TOTAL_DATA_LENGTH));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        StartDataPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        StartDataPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // expected length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(START_DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        StartDataPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = ByteUtils.join(
                TRANSACTION_ID.bytes(),
                TOTAL_DATA_LENGTH.bytes()
        );

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(START_DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        StartDataPacket actual = StartDataPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(START_DATA));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getTotalDataLength(), is(TOTAL_DATA_LENGTH));
        assertThat(actual.getPayload(), is(givenPayload));
    }
}
