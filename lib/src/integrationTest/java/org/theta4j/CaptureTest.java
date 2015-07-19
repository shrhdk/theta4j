/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.type.UINT32;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CaptureTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureTest.class);

    private static final CountDownLatch onObjectAdded = new CountDownLatch(1);

    private static final ThetaEventListener LISTENER = new ThetaEventAdapter() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            onObjectAdded.countDown();
            LOGGER.info("onObjectAdded: " + objectHandle);
        }
    };

    @Test
    public void initiateCapture() throws IOException, InterruptedException {
        try (Theta theta = new Theta()) {
            theta.addListener(LISTENER);
            theta.initiateCapture();
            if (!onObjectAdded.await(10, TimeUnit.SECONDS)) {
                Assert.fail("onObjectAdded event is timed out.");
            }
        }
        Thread.sleep(TestParameters.INTERVAL_MS);
    }
}
