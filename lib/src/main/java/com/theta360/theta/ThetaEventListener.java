package com.theta360.theta;

// TODO: Write Javadoc

/**
 * The listener interface for the events of RICOH THETA.
 *
 * @see com.theta360.theta.Theta#addListener(ThetaEventListener)
 * @see com.theta360.theta.Theta#removeListener(ThetaEventListener)
 */
public interface ThetaEventListener {
    /**
     * Notification of an object addition
     *
     * @param objectHandle The ObjectHandle of the added object.
     */
    void onObjectAdded(long objectHandle);

    void onCaptureStatusChanged();

    void onRecordingTimeChanged();

    void onRemainingRecordingTimeChanged();

    void onStoreFull(long storageID);

    void onCaptureComplete(long transactionID);
}
