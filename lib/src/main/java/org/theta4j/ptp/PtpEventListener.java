package org.theta4j.ptp;

import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;

import java.util.EventListener;

/**
 * Listener Set for PTP Event.
 */
public interface PtpEventListener extends EventListener {
    void onCancelTransaction();

    void onObjectAdded(UINT32 objectHandle);

    void onObjectRemoved(UINT32 objectHandle);

    void onStoreAdded(UINT32 storageID);

    void onStoreRemoved(UINT32 storageID);

    void onDevicePropChanged(UINT16 devicePropCode);

    void onObjectInfoChanged(UINT32 objectHandle);

    void onDeviceInfoChanged();

    void onRequestObjectTransfer(UINT32 objectHandle);

    void onStoreFull(UINT32 storageID);

    void onDeviceReset();

    void onStorageInfoChanged(UINT32 storageID);

    void onCaptureComplete(UINT32 transactionID);

    void onUnreportedStatus();

    void onVendorExtendedCode(Event event);

    void onError(Exception e);
}
