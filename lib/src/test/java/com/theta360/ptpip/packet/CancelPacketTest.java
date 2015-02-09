package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.CANCEL;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class CancelPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES];
    private static final UINT32 TRANSACTION_ID = new UINT32(0);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new CancelPacket(null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // expected
        byte[] expectedPayload = TRANSACTION_ID.bytes();

        // act
        CancelPacket packet = new CancelPacket(TRANSACTION_ID);

        // verify
        assertThat(packet.getType(), is(CANCEL));
        assertThat(packet.getTransactionID(), is(TRANSACTION_ID));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        CancelPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        CancelPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // expected length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(CANCEL, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        CancelPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = TRANSACTION_ID.bytes();

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(CANCEL, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacket.bytes()));

        // act
        CancelPacket actual = CancelPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(CANCEL));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getPayload(), is(givenPayload));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        CancelPacket packet1 = new CancelPacket(new UINT32(0));
        CancelPacket packet2 = new CancelPacket(new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        CancelPacket packet1 = new CancelPacket(TRANSACTION_ID);
        CancelPacket packet2 = new CancelPacket(TRANSACTION_ID);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        CancelPacket packet = new CancelPacket(new UINT32(0));

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        CancelPacket packet = new CancelPacket(TRANSACTION_ID);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        CancelPacket packet1 = new CancelPacket(new UINT32(0));
        CancelPacket packet2 = new CancelPacket(new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        CancelPacket packet = new CancelPacket(TRANSACTION_ID);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        CancelPacket packet1 = new CancelPacket(TRANSACTION_ID);
        CancelPacket packet2 = new CancelPacket(TRANSACTION_ID);

        // verify
        assertTrue(packet1.equals(packet2));
    }
}
