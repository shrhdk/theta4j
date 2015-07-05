package org.theta4j;

public abstract class ThetaEventAdapter implements ThetaEventListener {
    @Override
    public void onObjectAdded(long objectHandle) {
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
    public void onStoreFull(long storageID) {
    }

    @Override
    public void onCaptureComplete() {
    }
}
