package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.CANCEL;
import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CancelPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE];
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

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        CancelPacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);

        // act
        CancelPacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidLengthPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // expected length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(CANCEL, givenPayload);

        // act
        CancelPacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        byte[] givenPayload = TRANSACTION_ID.bytes();

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(CANCEL, givenPayload);

        // act
        CancelPacket actual = CancelPacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(CANCEL));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getPayload(), is(givenPayload));
    }
}
