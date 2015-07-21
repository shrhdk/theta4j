/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.theta4j.ptp.type.UINT32;

public abstract class ThetaEventAdapter implements ThetaEventListener {
    @Override
    public void onObjectAdded(UINT32 objectHandle) {
    }

    @Override
    public void onCaptureStatusChanged() {
    }

    @Override
    public void onRecordingTimeChanged() {
    }

    @Override
    public void onRemainingRecordingTimeChanged() {
    }

    @Override
    public void onStoreFull() {
    }

    @Override
    public void onCaptureComplete(UINT32 transactionID) {
    }
}
