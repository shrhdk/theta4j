/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.junit.Before;
import org.junit.Test;
import org.theta4j.ptp.code.EventCode;
import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PtpEventListenerSetTest {
    private static final UINT16 UNKNOWN_CODE = new UINT16(0b0000_0000);
    private static final UINT16 RESERVED_CODE = new UINT16(0b0100_0000);
    private static final UINT16 VENDOR_EXTENDED_CODE = new UINT16(0b1100_0000);
    private static final UINT32 SESSION_ID = new UINT32(0);
    private static final UINT32 TRANSACTION_ID = new UINT32(1);
    private static final UINT32 P1 = new UINT32(3);

    private PtpEventListenerSet set;
    private PtpEventListener listener1, listener2;

    // Set up

    @Before
    public void setUp() {
        set = new PtpEventListenerSet();
        listener1 = mock(PtpEventListener.class);
        listener2 = mock(PtpEventListener.class);

        set.add(listener1);
        set.add(listener2);
    }

    // Set

    @Test
    public void remove() {
        assertThat(set.remove(mock(PtpEventListener.class)), is(false));
        assertThat(set.size(), is(2));

        set.remove(listener1);
        assertThat(set.size(), is(1));

        set.remove(listener2);
        assertThat(set.size(), is(0));
    }

    @Test
    public void clear() {
        // act
        set.clear();

        // verify
        assertThat(set.size(), is(0));
    }

    @Test
    public void iterator() {
        // verify
        assertThat(set.iterator(), notNullValue());
    }

    @Test
    public void size() {
        // verify
        assertThat(set.size(), is(2));
    }

    // raise

    @Test
    public void raiseUndefined() {
        // given
        Event given = new Event(EventCode.UNDEFINED.value(), SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onError(any(RuntimeException.class));
        verify(listener2).onError(any(RuntimeException.class));
    }

    @Test
    public void raiseCancelTransaction() {
        // given
        Event given = new Event(EventCode.CANCEL_TRANSACTION.value(), SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onCancelTransaction();
        verify(listener2).onCancelTransaction();
    }

    @Test
    public void raiseObjectAdded() {
        // given
        Event given = new Event(EventCode.OBJECT_ADDED.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onObjectAdded(P1);
        verify(listener2).onObjectAdded(P1);
    }

    @Test
    public void raiseObjectRemoved() {
        // given
        Event given = new Event(EventCode.OBJECT_REMOVED.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onObjectRemoved(P1);
        verify(listener2).onObjectRemoved(P1);
    }

    @Test
    public void raiseStoreAdded() {
        // given
        Event given = new Event(EventCode.STORE_ADDED.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onStoreAdded(P1);
        verify(listener2).onStoreAdded(P1);
    }

    @Test
    public void raiseStoreRemoved() {
        // given
        Event given = new Event(EventCode.STORE_REMOVED.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onStoreRemoved(P1);
        verify(listener2).onStoreRemoved(P1);
    }

    @Test
    public void raiseDevicePropChanged() {
        // given
        Event given = new Event(EventCode.DEVICE_PROP_CHANGED.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onDevicePropChanged(new UINT16(P1.intValue()));
        verify(listener2).onDevicePropChanged(new UINT16(P1.intValue()));
    }

    @Test
    public void raiseObjectInfoChanged() {
        // given
        Event given = new Event(EventCode.OBJECT_INFO_CHANGED.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onObjectInfoChanged(P1);
        verify(listener2).onObjectInfoChanged(P1);
    }

    @Test
    public void raiseDeviceInfoChanged() {
        // given
        Event given = new Event(EventCode.DEVICE_INFO_CHANGED.value(), SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onDeviceInfoChanged();
        verify(listener2).onDeviceInfoChanged();
    }

    @Test
    public void raiseRequestObjectTransfer() {
        // given
        Event given = new Event(EventCode.REQUEST_OBJECT_TRANSFER.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onRequestObjectTransfer(P1);
        verify(listener2).onRequestObjectTransfer(P1);
    }

    @Test
    public void raiseStoreFull() {
        // given
        Event given = new Event(EventCode.STORE_FULL.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onStoreFull(P1);
        verify(listener2).onStoreFull(P1);
    }

    @Test
    public void raiseDeviceReset() {
        // given
        Event given = new Event(EventCode.DEVICE_RESET.value(), SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onDeviceReset();
        verify(listener2).onDeviceReset();
    }

    @Test
    public void raiseStorageInfoChanged() {
        // given
        Event given = new Event(EventCode.STORAGE_INFO_CHANGED.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onStorageInfoChanged(P1);
        verify(listener2).onStorageInfoChanged(P1);
    }

    @Test
    public void raiseCaptureComplete() {
        // given
        Event given = new Event(EventCode.CAPTURE_COMPLETE.value(), SESSION_ID, TRANSACTION_ID, P1);

        // act
        set.raise(given);

        // verify
        verify(listener1).onCaptureComplete(P1);
        verify(listener2).onCaptureComplete(P1);
    }

    @Test
    public void raiseUnreportedStatus() {
        // given
        Event given = new Event(EventCode.UNREPORTED_STATUS.value(), SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onUnreportedStatus();
        verify(listener2).onUnreportedStatus();
    }

    @Test
    public void raiseReservedCode() {
        // given
        Event given = new Event(RESERVED_CODE, SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onError(any(RuntimeException.class));
        verify(listener2).onError(any(RuntimeException.class));
    }

    @Test
    public void raiseVendorExtendedCode() {
        // given
        Event given = new Event(VENDOR_EXTENDED_CODE, SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onVendorExtendedCode(given);
        verify(listener2).onVendorExtendedCode(given);
    }

    @Test
    public void raiseUnknownCode() {
        // given
        Event given = new Event(UNKNOWN_CODE, SESSION_ID, TRANSACTION_ID);

        // act
        set.raise(given);

        // verify
        verify(listener1).onError(any(RuntimeException.class));
        verify(listener2).onError(any(RuntimeException.class));
    }
}
