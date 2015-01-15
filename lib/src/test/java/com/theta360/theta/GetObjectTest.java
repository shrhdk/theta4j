package com.theta360.theta;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpException;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Category(IntegrationTest.class)
public class GetObjectTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetObjectTest.class);
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

    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException, PtpException {
        theta = new Theta();
        theta.addListener(listener);
        theta.openSession(SESSION_ID);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException, PtpException {
        theta.closeSession();
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    // Operations

    @Test
    public void getObject() throws IOException, PtpException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("raw.jpg"))) {
            theta.getObject(objectHandles.get(2), file);
        }
    }

    @Test
    public void getThumb() throws IOException, PtpException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("thumb.jpg"))) {
            theta.getThumb(objectHandles.get(2), file);
        }
    }

    @Test
    public void getResizedImageObject() throws IOException, PtpException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("resized.jpg"))) {
            theta.getResizedImageObject(objectHandles.get(2), file);
        }
    }
}
