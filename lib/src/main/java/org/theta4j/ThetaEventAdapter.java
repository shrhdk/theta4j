/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.theta4j.ptp.type.UINT32;

/**
 * An abstract adapter class for receiving THETA events. The method in this class are empty. This class exists as convenience for creating listener objects.
 */
public abstract class ThetaEventAdapter implements ThetaEventListener {
    /**
     * {@inheritDoc}
     */
    @Override
    public void onObjectAdded(UINT32 objectHandle) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCaptureStatusChanged() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRecordingTimeChanged() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRemainingRecordingTimeChanged() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStoreFull() {
    }
}
