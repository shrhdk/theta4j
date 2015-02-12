package com.theta360.theta;

import com.theta360.ptp.PtpException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CaptureTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureTest.class);

    private static final CountDownLatch onObjectAdded = new CountDownLatch(1);

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(long objectHandle) {
            onObjectAdded.countDown();
            LOGGER.info("onObjectAdded: " + objectHandle);
        }

        @Override
        public void onCaptureStatusChanged() {
            LOGGER.info("onCaptureStatusChanged");
        }

        @Override
        public void onRecordingTimeChanged() {
            LOGGER.info("onRecordingTimeChanged");
        }

        @Override
        public void onRemainingRecordingTimeChanged() {
            LOGGER.info("onRemainingRecordingTimeChanged");
        }

        @Override
        public void onStoreFull(long storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(long transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    @Test
    public void initiateCapture() throws IOException, PtpException, InterruptedException {
        try (Theta theta = new Theta()) {
            theta.addListener(listener);
            theta.initiateCapture();
            if (!onObjectAdded.await(10, TimeUnit.SECONDS)) {
                Assert.fail("onObjectAdded event is timed out.");
            }
        }
        Thread.sleep(TestParameters.INTERVAL_MS);
    }
}
