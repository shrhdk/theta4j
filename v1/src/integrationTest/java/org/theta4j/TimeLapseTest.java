/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.After;
import org.junit.Test;
import org.theta4j.data.StillCaptureMode;
import org.theta4j.ptp.type.UINT32;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TimeLapseTest extends BaseThetaTest {
    private static final int NUM_OF_CAPTURE = 2;
    private static final int INTERVAL = 5000;
    private static final int POSTPONEMENT = 1000;

    private final CountDownLatch latch = new CountDownLatch(1);
    private final AtomicReference<UINT32> onCaptureCompleteTransactionID = new AtomicReference<>();
    private final List<UINT32> objectHandles = Collections.synchronizedList(new ArrayList<>());

    @After
    public void sleepAndDelete() throws IOException, InterruptedException {
        Thread.sleep(TestParameters.INTERVAL_AFTER_TIME_LAPSE);

        for (UINT32 objectHandle : objectHandles) {
            theta.deleteObject(objectHandle);
        }
    }

    @Test
    public void captureWithTimeLapseNumber() throws IOException, InterruptedException {
        theta.setTimelapseInterval(INTERVAL);
        theta.setTimelapseNumber(NUM_OF_CAPTURE);

        theta.setStillCaptureMode(StillCaptureMode.TIME_LAPSE);

        theta.addListener(new ThetaEventAdapter() {
            @Override
            public void onObjectAdded(UINT32 objectHandle) {
                objectHandles.add(objectHandle);
            }

            @Override
            public void onCaptureComplete(UINT32 transactionID) {
                onCaptureCompleteTransactionID.set(transactionID);
                latch.countDown();
            }
        });

        UINT32 transactionID = theta.initiateOpenCapture();

        latch.await();

        assertThat(transactionID, is(onCaptureCompleteTransactionID.get()));
        assertThat(objectHandles.size(), is(NUM_OF_CAPTURE));
    }

    @Test
    public void captureWithUnlimitedTimeLapseNumber() throws IOException, InterruptedException {
        theta.setTimelapseInterval(INTERVAL);
        theta.setTimelapseNumber(0);

        theta.setStillCaptureMode(StillCaptureMode.TIME_LAPSE);

        theta.addListener(new ThetaEventAdapter() {
            @Override
            public void onObjectAdded(UINT32 objectHandle) {
                objectHandles.add(objectHandle);
            }
        });

        theta.initiateOpenCapture();

        Thread.sleep(INTERVAL * NUM_OF_CAPTURE + POSTPONEMENT);

        theta.terminateOpenCapture();

        assertThat(objectHandles.size(), is(NUM_OF_CAPTURE));
    }
}
