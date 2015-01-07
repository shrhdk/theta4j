package com.theta360.ptp;

import com.theta360.ptp.data.GUID;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import com.theta360.theta.Theta;
import com.theta360.theta.ThetaEventListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

@Category(IntegrationTest.class)
public class PtpIpInitiatorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpIpInitiator.class);
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

    private PtpIpInitiator initiator;

    @Before
    public void connect() throws IOException {
        initiator = new PtpIpInitiator(new GUID(UUID.randomUUID()), Theta.IP_ADDRESS, Theta.TCP_PORT);
        initiator.addListener(listener);
    }

    @After
    public void sleep() throws InterruptedException {
        Thread.sleep(500);
    }

    @After
    public void close() throws IOException {
        initiator.close();
    }

    @Test
    public void getDeviceInfo() throws IOException {
        initiator.getDeviceInfo();
    }

    @Test
    public void openAndCloseSession() throws IOException {
    }

    @Test
    public void getObjectHandles() throws IOException {
        initiator.openSession(SESSION_ID);
        initiator.getObjectHandles();
        initiator.closeSession();
    }

    @Test
    public void initiateCapture() throws IOException {
        initiator.openSession(SESSION_ID);
        initiator.initiateCapture();
        initiator.closeSession();
    }
}
