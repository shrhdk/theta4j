package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import org.junit.Test;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static com.theta360.ptp.packet.PtpIpPacket.Type.OPERATION_RESPONSE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OperationResponsePacketTest {
    private static final byte[] PAYLOAD = new byte[UINT16.SIZE + UINT32.SIZE + UINT32.SIZE * 5];
    private static final UINT16 RESPONSE_CODE = new UINT16(0);
    private static final UINT32 TRANSACTION_ID = new UINT32(0);
    private static final UINT32 P1 = new UINT32(1);
    private static final UINT32 P2 = new UINT32(2);
    private static final UINT32 P3 = new UINT32(3);
    private static final UINT32 P4 = new UINT32(4);
    private static final UINT32 P5 = new UINT32(5);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullResponseCode() {
        // act
        new OperationResponsePacket(null, TRANSACTION_ID, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new OperationResponsePacket(RESPONSE_CODE, null, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam1() {
        // act
        new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID, null, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam2() {
        // act
        new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID, P1, null, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam3() {
        // act
        new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID, P1, P2, null, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam4() {
        // act
        new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID, P1, P2, P3, null, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam5() {
        // act
        new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID, P1, P2, P3, P4, null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // expected
        byte[] expectedPayload = ByteUtils.join(
                RESPONSE_CODE.bytes(),
                TRANSACTION_ID.bytes(),
                P1.bytes(), P2.bytes(), P3.bytes(), P4.bytes(), P5.bytes()
        );

        // act
        OperationResponsePacket packet = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // verify
        assertThat(packet.getType(), is(OPERATION_RESPONSE));
        assertThat(packet.getResponseCode(), is(RESPONSE_CODE));
        assertThat(packet.getTransactionID(), is(TRANSACTION_ID));
        assertThat(packet.getP1(), is(P1));
        assertThat(packet.getP2(), is(P2));
        assertThat(packet.getP3(), is(P3));
        assertThat(packet.getP4(), is(P4));
        assertThat(packet.getP5(), is(P5));
        assertThat(packet.getPayload(), is(expectedPayload));
    }

    // valueOf with error

    @Test(expected = NullPointerException.class)
    public void valueOfNull() throws PacketException {
        // act
        OperationResponsePacket.valueOf(null);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidType() throws PacketException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(invalidType, PAYLOAD);

        // act
        OperationResponsePacket.valueOf(givenPacket);
    }

    @Test(expected = PacketException.class)
    public void valueOfInvalidLengthPayload() throws PacketException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // expected length - 1

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(OPERATION_RESPONSE, givenPayload);

        // act
        OperationResponsePacket.valueOf(givenPacket);
    }

    // valueOf

    @Test
    public void valueOf() throws PacketException {
        // given
        byte[] givenPayload = ByteUtils.join(
                RESPONSE_CODE.bytes(),
                TRANSACTION_ID.bytes(),
                P1.bytes(), P2.bytes(), P3.bytes(), P4.bytes(), P5.bytes()
        );

        // arrange
        PtpIpPacket givenPacket = new PtpIpPacket(OPERATION_RESPONSE, givenPayload);

        // act
        OperationResponsePacket actual = OperationResponsePacket.valueOf(givenPacket);

        // verify
        assertThat(actual.getType(), is(OPERATION_RESPONSE));
        assertThat(actual.getResponseCode(), is(RESPONSE_CODE));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getP1(), is(P1));
        assertThat(actual.getP2(), is(P2));
        assertThat(actual.getP3(), is(P3));
        assertThat(actual.getP4(), is(P4));
        assertThat(actual.getP5(), is(P5));
        assertThat(actual.getPayload(), is(givenPayload));
    }
}
