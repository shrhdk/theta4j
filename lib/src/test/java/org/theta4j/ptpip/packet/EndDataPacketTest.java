package org.theta4j.ptpip.packet;

import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

import static org.theta4j.ptpip.packet.PtpIpPacket.Type.END_DATA;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class EndDataPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 TRANSACTION_ID = new UINT32(0);
    private static final byte[] DATA_PAYLOAD = new byte[]{0x00, 0x01, 0x02, 0x03};

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new EndDataPacket(null, DATA_PAYLOAD);
    }

    @Test(expected = NullPointerException.class)
    public void withNullDataPayload() {
        // act
        new EndDataPacket(TRANSACTION_ID, null);
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
        EndDataPacket packet = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertThat(packet.getType(), is(END_DATA));
        assertThat(packet.getTransactionID(), is(TRANSACTION_ID));
        assertThat(packet.getDataPayload(), is(DATA_PAYLOAD));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        EndDataPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        EndDataPacket.read(givenInputStream);
    }

    @Test(expected = EOFException.class)
    public void readTooShortPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // min length - 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(END_DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        EndDataPacket.read(givenInputStream);
    }

    @Test(expected = EOFException.class)
    public void readInsufficientDataPayload() throws IOException {
        // given (has length in header larger than actual)
        byte[] givenPacketBytes = ArrayUtils.join(
                new UINT32(100).bytes(),
                PtpIpPacket.Type.END_DATA.value().bytes(),
                TRANSACTION_ID.bytes(),
                DATA_PAYLOAD
        );

        // arrange
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        EndDataPacket.read(givenInputStream);
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
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(END_DATA, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        EndDataPacket actual = EndDataPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(END_DATA));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getDataPayload(), is(DATA_PAYLOAD));
        assertThat(actual.getPayload(), is(givenPayload));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        EndDataPacket packet1 = new EndDataPacket(new UINT32(0), DATA_PAYLOAD);
        EndDataPacket packet2 = new EndDataPacket(new UINT32(1), DATA_PAYLOAD);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentData() {
        // given
        EndDataPacket packet1 = new EndDataPacket(TRANSACTION_ID, new byte[]{});
        EndDataPacket packet2 = new EndDataPacket(TRANSACTION_ID, new byte[]{0x00});

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        EndDataPacket packet1 = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);
        EndDataPacket packet2 = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        EndDataPacket packet = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        EndDataPacket packet = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        EndDataPacket packet1 = new EndDataPacket(new UINT32(0), DATA_PAYLOAD);
        EndDataPacket packet2 = new EndDataPacket(new UINT32(1), DATA_PAYLOAD);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithData() {
        // given
        EndDataPacket packet1 = new EndDataPacket(TRANSACTION_ID, new byte[]{});
        EndDataPacket packet2 = new EndDataPacket(TRANSACTION_ID, new byte[]{0x00});

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        EndDataPacket packet = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        EndDataPacket packet1 = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);
        EndDataPacket packet2 = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        EndDataPacket packet = new EndDataPacket(TRANSACTION_ID, DATA_PAYLOAD);

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
        assertTrue(actual.contains("transactionID"));
        assertTrue(actual.contains("dataPayload"));
    }
}

