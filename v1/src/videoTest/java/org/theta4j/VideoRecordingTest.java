/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.After;
import org.junit.Test;
import org.theta4j.ptp.type.UINT32;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VideoRecordingTest extends BaseThetaTest {
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
    public void captureWithTimeLimit() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);

        theta.addListener(new ThetaEventAdapter() {
            @Override
            public void onObjectAdded(UINT32 objectHandle) {
                objectHandles.add(objectHandle);
                latch.countDown();
            }

            @Override
            public void onCaptureComplete(UINT32 transactionID) {
                onCaptureCompleteTransactionID.set(transactionID);
                latch.countDown();
            }
        });

        UINT32 transactionID = theta.initiateOpenCapture();

        // Waiting for 3 minutes time limit
        latch.await();

        assertThat(transactionID, is(onCaptureCompleteTransactionID.get()));
    }

    @Test
    public void captureAndStop() throws IOException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        theta.addListener(new ThetaEventAdapter() {
            @Override
            public void onObjectAdded(UINT32 objectHandle) {
                objectHandles.add(objectHandle);
                latch.countDown();
            }
        });

        theta.initiateOpenCapture();

        Thread.sleep(3000);

        theta.terminateOpenCapture();

        latch.await();
    }
}
