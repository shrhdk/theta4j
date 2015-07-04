package org.theta4j.ptp;

import org.theta4j.ptp.code.EventCode;
import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class PtpEventListenerSet extends AbstractSet<PtpEventListener> implements PtpEventListener {
    private final Set<PtpEventListener> listeners = new CopyOnWriteArraySet<>();

    // Set<PtpEventListener>

    @Override
    public boolean add(PtpEventListener listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean remove(Object o) {
        return listeners.remove(o);
    }

    @Override
    public void clear() {
        listeners.clear();
    }

    @Override
    public Iterator<PtpEventListener> iterator() {
        return listeners.iterator();
    }

    @Override
    public int size() {
        return listeners.size();
    }

    // PtpEventListener

    @Override
    public void onCancelTransaction() {
        for (PtpEventListener listener : listeners) {
            listener.onCancelTransaction();
        }
    }

    @Override
    public void onObjectAdded(UINT32 objectHandle) {
        Validators.notNull("objectHandle", objectHandle);

        for (PtpEventListener listener : listeners) {
            listener.onObjectAdded(objectHandle);
        }
    }

    @Override
    public void onObjectRemoved(UINT32 objectHandle) {
        Validators.notNull("objectHandle", objectHandle);

        for (PtpEventListener listener : listeners) {
            listener.onObjectRemoved(objectHandle);
        }
    }

    @Override
    public void onStoreAdded(UINT32 storageID) {
        Validators.notNull("storageID", storageID);

        for (PtpEventListener listener : listeners) {
            listener.onStoreAdded(storageID);
        }
    }

    @Override
    public void onStoreRemoved(UINT32 storageID) {
        Validators.notNull("storageID", storageID);

        for (PtpEventListener listener : listeners) {
            listener.onStoreRemoved(storageID);
        }
    }

    @Override
    public void onDevicePropChanged(UINT16 devicePropCode) {
        Validators.notNull("devicePropCode", devicePropCode);

        for (PtpEventListener listener : listeners) {
            listener.onDevicePropChanged(devicePropCode);
        }
    }

    @Override
    public void onObjectInfoChanged(UINT32 objectHandle) {
        Validators.notNull("objectHandle", objectHandle);

        for (PtpEventListener listener : listeners) {
            listener.onObjectInfoChanged(objectHandle);
        }
    }

    @Override
    public void onDeviceInfoChanged() {
        for (PtpEventListener listener : listeners) {
            listener.onDeviceInfoChanged();
        }
    }

    @Override
    public void onRequestObjectTransfer(UINT32 objectHandle) {
        Validators.notNull("objectHandle", objectHandle);

        for (PtpEventListener listener : listeners) {
            listener.onRequestObjectTransfer(objectHandle);
        }
    }

    @Override
    public void onStoreFull(UINT32 storageID) {
        Validators.notNull("storageID", storageID);

        for (PtpEventListener listener : listeners) {
            listener.onStoreFull(storageID);
        }
    }

    @Override
    public void onDeviceReset() {
        for (PtpEventListener listener : listeners) {
            listener.onDeviceReset();
        }
    }

    @Override
    public void onStorageInfoChanged(UINT32 storageID) {
        Validators.notNull("storageID", storageID);

        for (PtpEventListener listener : listeners) {
            listener.onStorageInfoChanged(storageID);
        }
    }

    @Override
    public void onCaptureComplete(UINT32 transactionID) {
        Validators.notNull("transactionID", transactionID);

        for (PtpEventListener listener : listeners) {
            listener.onCaptureComplete(transactionID);
        }
    }

    @Override
    public void onUnreportedStatus() {
        for (PtpEventListener listener : listeners) {
            listener.onUnreportedStatus();
        }
    }

    @Override
    public void onVendorExtendedCode(Event event) {
        Validators.notNull("event", event);

        for (PtpEventListener listener : listeners) {
            listener.onVendorExtendedCode(event);
        }
    }

    @Override
    public void onError(Exception e) {
        Validators.notNull("e", e);

        for (PtpEventListener listener : listeners) {
            listener.onError(e);
        }
    }

    // Utility method

    public void raise(Event event) {
        Validators.notNull("event", event);

        UINT16 eventCode = event.getEventCode();
        UINT32 p1 = event.getP1();

        if (eventCode.equals(EventCode.UNDEFINED.value())) {
            onError(new RuntimeException("Undefined Event Code: " + eventCode));
        } else if (eventCode.equals(EventCode.CANCEL_TRANSACTION.value())) {
            onCancelTransaction();
        } else if (eventCode.equals(EventCode.OBJECT_ADDED.value())) {
            onObjectAdded(p1);
        } else if (eventCode.equals(EventCode.OBJECT_REMOVED.value())) {
            onObjectRemoved(p1);
        } else if (eventCode.equals(EventCode.STORE_ADDED.value())) {
            onStoreAdded(p1);
        } else if (eventCode.equals(EventCode.STORE_REMOVED.value())) {
            onStoreRemoved(p1);
        } else if (eventCode.equals(EventCode.DEVICE_PROP_CHANGED.value())) {
            onDevicePropChanged(new UINT16(p1.intValue()));
        } else if (eventCode.equals(EventCode.OBJECT_INFO_CHANGED.value())) {
            onObjectInfoChanged(p1);
        } else if (eventCode.equals(EventCode.DEVICE_INFO_CHANGED.value())) {
            onDeviceInfoChanged();
        } else if (eventCode.equals(EventCode.REQUEST_OBJECT_TRANSFER.value())) {
            onRequestObjectTransfer(p1);
        } else if (eventCode.equals(EventCode.STORE_FULL.value())) {
            onStoreFull(p1);
        } else if (eventCode.equals(EventCode.DEVICE_RESET.value())) {
            onDeviceReset();
        } else if (eventCode.equals(EventCode.STORAGE_INFO_CHANGED.value())) {
            onStorageInfoChanged(p1);
        } else if (eventCode.equals(EventCode.CAPTURE_COMPLETE.value())) {
            onCaptureComplete(p1);
        } else if (eventCode.equals(EventCode.UNREPORTED_STATUS.value())) {
            onUnreportedStatus();
        } else if (EventCode.isReservedCode(eventCode)) {
            onError(new RuntimeException("Reserved Event Code: " + eventCode));
        } else if (EventCode.isVendorExtendedCode(eventCode)) {
            onVendorExtendedCode(event);
        } else {
            onError(new RuntimeException("Unknown Event Code: " + eventCode));
        }
    }
}
