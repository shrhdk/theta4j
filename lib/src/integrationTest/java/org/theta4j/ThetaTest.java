package org.theta4j;

import org.theta4j.ptp.PtpException;
import org.theta4j.ptp.data.ObjectInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ThetaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaTest.class);

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(long objectHandle) {
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

    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException, PtpException {
        theta = new Theta();
        theta.addListener(listener);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException, PtpException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    // Operations

    @Test
    public void getDeviceInfo() throws IOException, PtpException {
        LOGGER.info("Device Info" + theta.getDeviceInfo());
    }

    @Test
    public void getObjectHandles() throws IOException, PtpException {
        LOGGER.info("Object Handles: " + theta.getObjectHandles());
    }

    @Test
    public void getObjectInfo() throws IOException, PtpException {
        List<Long> objectHandles = theta.getObjectHandles();
        ObjectInfo objectInfo = theta.getObjectInfo(objectHandles.get(0));
        LOGGER.info("Object Info: " + objectInfo);
    }
}
