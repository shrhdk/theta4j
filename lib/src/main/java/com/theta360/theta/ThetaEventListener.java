package com.theta360.theta;

import com.theta360.ptp.type.UINT32;

public interface ThetaEventListener {
    void onObjectAdded(UINT32 objectHandle);

    void onDevicePropChanged(UINT32 devicePropCode);

    void onStoreFull(UINT32 storageID);

    void onCaptureComplete(UINT32 transactionID);
}
