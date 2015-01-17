package com.theta360.theta;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpException;
import com.theta360.ptp.data.ObjectInfo;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Category(IntegrationTest.class)
public class ThetaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaTest.class);

    private static ThetaEventListener listener = new ThetaEventListener() {
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
        List<UINT32> objectHandles = theta.getObjectHandles();
        ObjectInfo objectInfo = theta.getObjectInfo(objectHandles.get(0));
        LOGGER.info("Object Info: " + objectInfo);
    }
}
