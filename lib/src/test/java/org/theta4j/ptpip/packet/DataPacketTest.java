package org.theta4j.ptpip.packet;

import org.junit.Test;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.DATA;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;

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
        byte[] expectedPayload = ArrayUtils.join(
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
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        DataPacket.read(givenInputStream);
    }

    @Test(expected = EOFException.class)
    public void readTooShortPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // min length - 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        DataPacket.read(givenInputStream);
    }

    @Test(expected = EOFException.class)
    public void readInsufficientDataPayload() throws IOException {
        // given (has length in header larger than actual)
        byte[] givenPacketBytes = ArrayUtils.join(
                new UINT32(100).bytes(),
                PtpIpPacket.Type.DATA.value().bytes(),
                TRANSACTION_ID.bytes(),
                DATA_PAYLOAD
        );

        // arrange
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        DataPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = ArrayUtils.join(
                TRANSACTION_ID.bytes(),
                DATA_PAYLOAD
        );

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        DataPacket actual = DataPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(DATA));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getDataPayload(), is(DATA_PAYLOAD));
        assertThat(actual.getPayload(), is(givenPayload));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        DataPacket packet1 = new DataPacket(new UINT32(0), DATA_PAYLOAD);
        DataPacket packet2 = new DataPacket(new UINT32(1), DATA_PAYLOAD);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentData() {
        // given
        DataPacket packet1 = new DataPacket(TRANSACTION_ID, new byte[]{});
        DataPacket packet2 = new DataPacket(TRANSACTION_ID, new byte[]{0x00});

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        DataPacket packet1 = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);
        DataPacket packet2 = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        DataPacket packet = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        DataPacket packet = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        DataPacket packet1 = new DataPacket(new UINT32(0), DATA_PAYLOAD);
        DataPacket packet2 = new DataPacket(new UINT32(1), DATA_PAYLOAD);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithData() {
        // given
        DataPacket packet1 = new DataPacket(TRANSACTION_ID, new byte[]{});
        DataPacket packet2 = new DataPacket(TRANSACTION_ID, new byte[]{0x00});

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        DataPacket packet = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        DataPacket packet1 = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);
        DataPacket packet2 = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        DataPacket packet = new DataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
        assertTrue(actual.contains("transactionID"));
    }
}
