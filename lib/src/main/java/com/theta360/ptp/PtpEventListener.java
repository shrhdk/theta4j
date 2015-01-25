package com.theta360.ptp;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;

/**
 * Listener Set for PTP Event.
 */
public interface PtpEventListener {
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

    void onVendorExtendedCode(UINT16 eventCode, UINT32 p1, UINT32 p2, UINT32 p3);

    void onError(Exception e);
}
