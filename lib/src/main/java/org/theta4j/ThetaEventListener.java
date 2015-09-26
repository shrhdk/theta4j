/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.theta4j.ptp.type.UINT32;

import java.util.EventListener;

/**
 * An interface for receiving THETA events.
 *
 * @see Theta#addListener(ThetaEventListener)
 * @see Theta#removeListener(ThetaEventListener)
 */
public interface ThetaEventListener extends EventListener {
    /**
     * Invoked when a new object is added.
     *
     * @param objectHandle The ObjectHandle of the added object.
     */
    void onObjectAdded(UINT32 objectHandle);

    /**
     * Invoked when the capture status of THETA is changed.
     */
    void onCaptureStatusChanged();

    /**
     * Invoked when video recording time is updated.
     */
    void onRecordingTimeChanged();

    /**
     * Invoked when remaining video recording time is updated.
     */
    void onRemainingRecordingTimeChanged();

    /**
     * Invoked when the storage of THETA faced into the limit.
     */
    void onStoreFull();

    /**
     * Invoked when capturing is complete.
     */
    void onCaptureComplete(UINT32 transactionID);
}
