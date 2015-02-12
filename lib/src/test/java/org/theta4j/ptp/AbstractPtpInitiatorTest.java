package org.theta4j.ptp;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.code.OperationCode;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AbstractPtpInitiatorTest {
    private static final Code<UINT16> OPERATION_CODE = OperationCode.UNDEFINED;
    private static final UINT32 P1 = new UINT32(1);
    private static final UINT32 P2 = new UINT32(2);
    private static final UINT32 P3 = new UINT32(3);
    private static final UINT32 P4 = new UINT32(4);

    private PtpInitiator sub;

    @Before
    public void setUp() {
        sub = mock(AbstractPtpInitiator.class, CALLS_REAL_METHODS);
    }

    // sendOperation

    @Test
    public void sendOperation() throws IOException {
        // act
        sub.sendOperation(OPERATION_CODE);

        // verify
        verify(sub).sendOperation(
                OPERATION_CODE,
                UINT32.ZERO, UINT32.ZERO, UINT32.ZERO, UINT32.ZERO, UINT32.ZERO);
    }

    @Test
    public void sendOperationWithP1() throws IOException {
        // act
        sub.sendOperation(OPERATION_CODE, P1);

        // verify
        verify(sub).sendOperation(
                OPERATION_CODE,
                P1, UINT32.ZERO, UINT32.ZERO, UINT32.ZERO, UINT32.ZERO);
    }

    @Test
    public void sendOperationWithP12() throws IOException {
        // act
        sub.sendOperation(OPERATION_CODE, P1, P2);

        // verify
        verify(sub).sendOperation(
                OPERATION_CODE,
                P1, P2, UINT32.ZERO, UINT32.ZERO, UINT32.ZERO);
    }

    @Test
    public void sendOperationWithP123() throws IOException {
        // act
        sub.sendOperation(OPERATION_CODE, P1, P2, P3);

        // verify
        verify(sub).sendOperation(
                OPERATION_CODE,
                P1, P2, P3, UINT32.ZERO, UINT32.ZERO);
    }

    @Test
    public void sendOperationWithP1234() throws IOException {
        // act
        sub.sendOperation(OPERATION_CODE, P1, P2, P3, P4);

        // verify
        verify(sub).sendOperation(
                OPERATION_CODE,
                P1, P2, P3, P4, UINT32.ZERO);
    }
}
