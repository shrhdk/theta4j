/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.theta4j.ptp.data.Event;

import java.util.EventListener;

/**
 * An interface for receiving PTP event.
 */
public interface PtpEventListener extends EventListener {
    /**
     * Invoked when PTP event is occurred.
     *
     * @param event Event object.
     */
    void onEvent(Event event);
}
