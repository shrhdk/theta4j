package org.theta4j.ptpip.packet;

import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.theta4j.ptpip.packet.PtpIpPacket.Type.INIT_EVENT_REQUEST;
import static org.theta4j.ptpip.packet.PtpIpPacket.Type.OPERATION_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class OperationRequestPacketTest {
    private static final byte[] PAYLOAD = new byte[UINT32.SIZE_IN_BYTES + UINT16.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES * 5];
    private static final UINT32 DATA_PHASE_INFO = new UINT32(0);
    private static final UINT16 OPERATION_CODE = new UINT16(0);
    private static final UINT32 TRANSACTION_ID = new UINT32(0);
    private static final UINT32 P1 = new UINT32(1);
    private static final UINT32 P2 = new UINT32(2);
    private static final UINT32 P3 = new UINT32(3);
    private static final UINT32 P4 = new UINT32(4);
    private static final UINT32 P5 = new UINT32(5);

    // Constructor with error

    @Test(expected = NullPointerException.class)
    public void withNullDataPhaseInfo() {
        // act
        new OperationRequestPacket(null, OPERATION_CODE, TRANSACTION_ID, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullOperationCode() {
        // act
        new OperationRequestPacket(DATA_PHASE_INFO, null, TRANSACTION_ID, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, null, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam1() {
        // act
        new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID, null, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam2() {
        // act
        new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID, P1, null, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam3() {
        // act
        new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID, P1, P2, null, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam4() {
        // act
        new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID, P1, P2, P3, null, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullParam5() {
        // act
        new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID, P1, P2, P3, P4, null);
    }

    // Constructor

    @Test
    public void constructAndGet() {
        // expected
        byte[] expectedPayload = ArrayUtils.join(
                DATA_PHASE_INFO.bytes(),
                OPERATION_CODE.bytes(),
                TRANSACTION_ID.bytes(),
                P1.bytes(), P2.bytes(), P3.bytes(), P4.bytes(), P5.bytes()
        );

        // act
        OperationRequestPacket packet = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // verify
        assertThat(packet.getType(), is(OPERATION_REQUEST));
        assertThat(packet.getDataPhaseInfo(), is(DATA_PHASE_INFO));
        assertThat(packet.getOperationCode(), is(OPERATION_CODE));
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
        OperationRequestPacket.read(null);
    }

    @Test(expected = IOException.class)
    public void readInvalidType() throws IOException {
        // given
        PtpIpPacket.Type invalidType = INIT_EVENT_REQUEST;

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(invalidType, PAYLOAD);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        OperationRequestPacket.read(givenInputStream);
    }

    @Test(expected = IOException.class)
    public void readInvalidLengthPayload() throws IOException {
        // given
        byte[] givenPayload = new byte[PAYLOAD.length - 1];  // expected length - 1

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(OPERATION_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        OperationRequestPacket.read(givenInputStream);
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] givenPayload = ArrayUtils.join(
                DATA_PHASE_INFO.bytes(),
                OPERATION_CODE.bytes(),
                TRANSACTION_ID.bytes(),
                P1.bytes(), P2.bytes(), P3.bytes(), P4.bytes(), P5.bytes()
        );

        // arrange
        byte[] givenPacketBytes = PtpIpPacketTestUtils.bytes(OPERATION_REQUEST, givenPayload);
        PtpInputStream givenInputStream = new PtpInputStream(new ByteArrayInputStream(givenPacketBytes));

        // act
        OperationRequestPacket actual = OperationRequestPacket.read(givenInputStream);

        // verify
        assertThat(actual.getType(), is(OPERATION_REQUEST));
        assertThat(actual.getDataPhaseInfo(), is(DATA_PHASE_INFO));
        assertThat(actual.getOperationCode(), is(OPERATION_CODE));
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
    public void hashCodeOfDifferentDataPhaseInfo() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(new UINT32(0), OPERATION_CODE, TRANSACTION_ID);
        OperationRequestPacket packet2 = new OperationRequestPacket(new UINT32(1), OPERATION_CODE, TRANSACTION_ID);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentOperationCode() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, new UINT16(0), TRANSACTION_ID);
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, new UINT16(1), TRANSACTION_ID);

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP1() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP2() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP3() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP4() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP5() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(1));

        // verify
        assertThat(packet1.hashCode(), not(packet2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);

        // verify
        assertThat(packet1.hashCode(), is(packet2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        OperationRequestPacket packet = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID);

        // verify
        assertFalse(packet.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        OperationRequestPacket packet = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID);

        // verify
        assertFalse(packet.equals("foo"));
    }

    @Test
    public void notEqualsWithDataPhaseInfo() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(new UINT32(0), OPERATION_CODE, TRANSACTION_ID);
        OperationRequestPacket packet2 = new OperationRequestPacket(new UINT32(1), OPERATION_CODE, TRANSACTION_ID);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithOperationCode() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, new UINT16(0), TRANSACTION_ID);
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, new UINT16(1), TRANSACTION_ID);

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP1() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP2() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP3() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP4() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    @Test
    public void notEqualsWithP5() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(0));
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, new UINT32(1));

        // verify
        assertFalse(packet1.equals(packet2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        OperationRequestPacket packet = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID);

        // verify
        assertTrue(packet.equals(packet));
    }

    @Test
    public void equals() {
        // given
        OperationRequestPacket packet1 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);
        OperationRequestPacket packet2 = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID,
                P1, P2, P3, P4, P5);

        // verify
        assertTrue(packet1.equals(packet2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        OperationRequestPacket packet = new OperationRequestPacket(DATA_PHASE_INFO, OPERATION_CODE, TRANSACTION_ID);

        // act
        String actual = packet.toString();

        // verify
        assertTrue(actual.contains(packet.getClass().getSimpleName()));
        assertTrue(actual.contains("dataPhaseInfo"));
        assertTrue(actual.contains("operationCode"));
        assertTrue(actual.contains("transactionID"));
        assertTrue(actual.contains("p1"));
        assertTrue(actual.contains("p2"));
        assertTrue(actual.contains("p3"));
        assertTrue(actual.contains("p4"));
        assertTrue(actual.contains("p5"));
    }
}
