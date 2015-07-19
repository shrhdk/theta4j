/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.theta4j.ptp.type.UINT32;

public interface InitiateCaptureCallback {
    void onObjectAdded(UINT32 objectHandle);

    void onStoreFull();

    void onCaptureComplete();
}
