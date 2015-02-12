package org.theta4j.ptpip.packet;

import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.util.ArrayUtils;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class StartDataPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES + UINT64.SIZE_IN_BYTES];
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
        byte[] expectedPayload = ArrayUtils.join(
                TRANSACTION_ID.bytes(),
                TOTAL_DATA_LENGTH.bytes()
        );

        // act
        StartDataPacket packet = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // verify
        assertThat(packet.getType(), Is.is(PtpIpPacket.Type.START_DATA));
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
        PtpIpPacket.Type invalidType = PtpIpPacket.Type.INIT_EVENT_REQUEST;

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        StartDataPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // expected length - 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PtpIpPacket.Type.START_DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        StartDataPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = ArrayUtils.join(
                TRANSACTION_ID.bytes(),
                TOTAL_DATA_LENGTH.bytes()
        );

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(PtpIpPacket.Type.START_DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        StartDataPacket actual = StartDataPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), Is.is(PtpIpPacket.Type.START_DATA));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getTotalDataLength(), is(TOTAL_DATA_LENGTH));
        assertThat(actual.getPayload(), is(givenPayload));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        StartDataPacket packet1 = new StartDataPacket(new UINT32(0), TOTAL_DATA_LENGTH);
        StartDataPacket packet2 = new StartDataPacket(new UINT32(1), TOTAL_DATA_LENGTH);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentTotalDataLength() {
        // given
        StartDataPacket packet1 = new StartDataPacket(TRANSACTION_ID, new UINT64(0));
        StartDataPacket packet2 = new StartDataPacket(TRANSACTION_ID, new UINT64(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        StartDataPacket packet1 = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);
        StartDataPacket packet2 = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        StartDataPacket packet = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        StartDataPacket packet = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        StartDataPacket packet1 = new StartDataPacket(new UINT32(0), TOTAL_DATA_LENGTH);
        StartDataPacket packet2 = new StartDataPacket(new UINT32(1), TOTAL_DATA_LENGTH);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithTotalDataLength() {
        // given
        StartDataPacket packet1 = new StartDataPacket(TRANSACTION_ID, new UINT64(0));
        StartDataPacket packet2 = new StartDataPacket(TRANSACTION_ID, new UINT64(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        StartDataPacket packet = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        StartDataPacket packet1 = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);
        StartDataPacket packet2 = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        StartDataPacket packet = new StartDataPacket(TRANSACTION_ID, TOTAL_DATA_LENGTH);

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
        assertTrue(actual.contains("transactionID"));
        assertTrue(actual.contains("totalDataLength"));
    }
}
