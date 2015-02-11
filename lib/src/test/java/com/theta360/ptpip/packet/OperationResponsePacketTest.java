package com.theta360.ptpip.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.theta360.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static com.theta360.ptpip.packet.PtpIpPacket.Type.OPERATION_RESPONSE;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class OperationResponsePacketTest {
    private static final byte[] PAYLOAD = new byte[UINT16.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES * 5];
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

    // read with error

    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        // act
        OperationResponsePacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        OperationResponsePacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // expected length - 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(OPERATION_RESPONSE, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        OperationResponsePacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = ByteUtils.join(
                RESPONSE_CODE.bytes(),
                TRANSACTION_ID.bytes(),
                P1.bytes(), P2.bytes(), P3.bytes(), P4.bytes(), P5.bytes()
        );

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(OPERATION_RESPONSE, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        OperationResponsePacket actual = OperationResponsePacket.read(givenInputStream);

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

    // hashCode

    @Test
    public void hashCodeOfDifferentOperationCode() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(new UINT16(0), TRANSACTION_ID);
        OperationResponsePacket packet2 = new OperationResponsePacket(new UINT16(1), TRANSACTION_ID);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP1() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP2() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP3() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP4() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP5() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        OperationResponsePacket packet = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        OperationResponsePacket packet = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithOperationCode() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(new UINT16(0), TRANSACTION_ID);
        OperationResponsePacket packet2 = new OperationResponsePacket(new UINT16(1), TRANSACTION_ID);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP1() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP2() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP3() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP4() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP5() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(0));
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        OperationResponsePacket packet = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        OperationResponsePacket packet1 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);
        OperationResponsePacket packet2 = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        OperationResponsePacket packet = new OperationResponsePacket(RESPONSE_CODE, TRANSACTION_ID);

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
        assertTrue(actual.contains("responseCode"));
        assertTrue(actual.contains("transactionID"));
        assertTrue(actual.contains("p1"));
        assertTrue(actual.contains("p2"));
        assertTrue(actual.contains("p3"));
        assertTrue(actual.contains("p4"));
        assertTrue(actual.contains("p5"));
    }
}
