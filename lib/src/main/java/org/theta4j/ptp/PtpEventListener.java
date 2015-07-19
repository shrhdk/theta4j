/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.theta4j.ptp.data.Event;

import java.util.EventListener;

/**
 * Listener Set for PTP Event.
 */
public interface PtpEventListener extends EventListener {
    void onEvent(Event event);
}
