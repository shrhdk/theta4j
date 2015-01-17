package com.theta360.theta;

import com.theta360.ptp.PtpException;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

@Category(IntegrationTest.class)
public class CaptureTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureTest.class);

    private static final CountDownLatch onObjectAdded = new CountDownLatch(1);

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            onObjectAdded.countDown();
            LOGGER.info("onObjectAdded: " + objectHandle);
        }

        @Override
        public void onDevicePropChanged(UINT32 devicePropCode) {
            LOGGER.info("onDevicePropChanged: " + devicePropCode);
        }

        @Override
        public void onStoreFull(UINT32 storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(UINT32 transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    @Test
    public void initiateCapture() throws IOException, PtpException, InterruptedException {
        try (Theta theta = new Theta()) {
            theta.addListener(listener);
            theta.initiateCapture();
            if (!onObjectAdded.await(10, TimeUnit.SECONDS)) {
                fail("onObjectAdded event is timed out.");
            }
        }
        Thread.sleep(TestParameters.INTERVAL_MS);
    }
}
