package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.UnitTest;
import com.theta360.util.ByteUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.DATA;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class DataPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 TRANSACTION_ID = new UINT32(0);
    private static final byte[] DATA_PAYLOAD = new byte[]{0x00, 0x01, 0x02, 0x03};

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new DataPacket(null, DATA_PAYLOAD);
    }

    @Test(expected = NullPointerException.class)
    public void withNullDataPayload() {
        // act
        new DataPacket(TRANSACTION_ID, null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // expected
        byte[] expectedPayload = ByteUtils.join(
                TRANSACTION_ID.bytes(),
                DATA_PAYLOAD
        );

        // act
        DataPacket packet = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertThat(packet.getType(), is(DATA));
        assertThat(packet.getTransactionID(), is(TRANSACTION_ID));
        assertThat(packet.getDataPayload(), is(DATA_PAYLOAD));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        DataPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        DataPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readTooShortPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // min length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        DataPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = ByteUtils.join(
                TRANSACTION_ID.bytes(),
                DATA_PAYLOAD
        );

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        DataPacket actual = DataPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(DATA));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getDataPayload(), is(DATA_PAYLOAD));
        assertThat(actual.getPayload(), is(givenPayload));
    }
}
