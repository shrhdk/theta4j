package com.theta360.theta;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;

public abstract class ThetaEventListener implements PtpEventListener {
    @Override
    public void onCancelTransaction() {
        // NOP
    }

    @Override
    public void onObjectRemoved(UINT32 objectHandle) {
        // NOP
    }

    @Override
    public void onStoreAdded(UINT32 storageID) {
        // NOP
    }

    @Override
    public void onStoreRemoved(UINT32 storageID) {
        // NOP
    }

    @Override
    public void onObjectInfoChanged(UINT32 objectHandle) {
        // NOP
    }

    @Override
    public void onDeviceInfoChanged() {
        // NOP
    }

    @Override
    public void onRequestObjectTransfer(UINT32 objectHandle) {
        // NOP
    }

    @Override
    public void onDeviceReset() {
        // NOP
    }

    @Override
    public void onStorageInfoChanged(UINT32 storageID) {
        // NOP
    }

    @Override
    public void onUnreportedStatus() {
        // NOP
    }

    @Override
    public void onVendorExtendedCode(UINT16 eventCode, UINT32 p1, UINT32 p2, UINT32 p3) {
        // NOP
    }

    @Override
    public void onError(Exception e) {
        // NOP
    }
}
