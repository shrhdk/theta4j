/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;

public abstract class PtpEventAdapter implements PtpEventListener {
    @Override
    public void onCancelTransaction() {
    }

    @Override
    public void onObjectAdded(UINT32 objectHandle) {
    }

    @Override
    public void onObjectRemoved(UINT32 objectHandle) {
    }

    @Override
    public void onStoreAdded(UINT32 storageID) {
    }

    @Override
    public void onStoreRemoved(UINT32 storageID) {
    }

    @Override
    public void onDevicePropChanged(UINT16 devicePropCode) {
    }

    @Override
    public void onObjectInfoChanged(UINT32 objectHandle) {
    }

    @Override
    public void onDeviceInfoChanged() {
    }

    @Override
    public void onRequestObjectTransfer(UINT32 objectHandle) {
    }

    @Override
    public void onStoreFull(UINT32 storageID) {
    }

    @Override
    public void onDeviceReset() {
    }

    @Override
    public void onStorageInfoChanged(UINT32 storageID) {
    }

    @Override
    public void onCaptureComplete(UINT32 transactionID) {
    }

    @Override
    public void onUnreportedStatus() {
    }

    @Override
    public void onVendorExtendedCode(Event event) {
    }

    @Override
    public void onError(Exception e) {
    }
}
