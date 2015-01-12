package com.theta360.theta;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Category(IntegrationTest.class)
public class AsyncThetaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaTest.class);
    private static final UINT32 SESSION_ID = new UINT32(1);

    private static PtpEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
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
    public void initiateCapture() throws IOException {
        Theta theta = new Theta();
        theta.addListener(listener);
        theta.openSession(SESSION_ID);
        theta.initiateCapture();
        theta.closeSession();
        theta.close();
    }
}
