/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.theta4j.ptp.type.UINT32;

import java.util.EventListener;

/**
 * The listener interface for the events of RICOH THETA.
 *
 * @see Theta#addListener(ThetaEventListener)
 * @see Theta#removeListener(ThetaEventListener)
 */
public interface ThetaEventListener extends EventListener {
    /**
     * Notification of an object addition
     *
     * @param objectHandle The ObjectHandle of the added object.
     */
    void onObjectAdded(UINT32 objectHandle);

    void onCaptureStatusChanged();

    void onRecordingTimeChanged();

    void onRemainingRecordingTimeChanged();

    void onStoreFull();

    void onCaptureComplete(UINT32 transactionID);
}
