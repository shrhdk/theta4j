package com.theta360.theta;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpInitiator;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Category(IntegrationTest.class)
public class ThetaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpInitiator.class);
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

    private Theta theta;

    @Before
    public void connect() throws IOException {
        theta = new Theta();
        theta.addListener(listener);
    }

    @After
    public void sleep() throws InterruptedException {
        Thread.sleep(500);
    }

    @After
    public void close() throws IOException {
        theta.close();
    }

    // Operations

    @Test
    public void getDeviceInfo() throws IOException {
        theta.getDeviceInfo();
    }

    @Test
    public void openAndCloseSession() throws IOException {
    }

    @Test
    public void getObjectHandles() throws IOException {
        theta.openSession(SESSION_ID);
        theta.getObjectHandles();
        theta.closeSession();
    }

    @Test
    public void getObject() throws IOException {
        try (FileOutputStream file = new FileOutputStream(new File("raw.jpg"))) {
            theta.openSession(SESSION_ID);
            List<UINT32> objectHandles = theta.getObjectHandles();
            theta.getObject(objectHandles.get(2), file);
            theta.closeSession();
        }
    }

    @Test
    public void getThumb() throws IOException {
        try (FileOutputStream file = new FileOutputStream(new File("thumb.jpg"))) {
            theta.openSession(SESSION_ID);
            List<UINT32> objectHandles = theta.getObjectHandles();
            theta.getThumb(objectHandles.get(2), file);
            theta.closeSession();
        }
    }

    @Test
    public void initiateCapture() throws IOException {
        theta.openSession(SESSION_ID);
        theta.initiateCapture();
        theta.closeSession();
    }

    @Test
    public void getResizedImageObject() throws IOException {
        try (FileOutputStream file = new FileOutputStream(new File("resized.jpg"))) {
            theta.openSession(SESSION_ID);
            List<UINT32> objectHandles = theta.getObjectHandles();
            theta.getResizedImageObject(objectHandles.get(2), file);
            theta.closeSession();
        }
    }

    // Property Getter

    @Test
    public void getBatteryLevel() throws IOException {
        theta.openSession(SESSION_ID);
        System.out.println("Battery Level: " + theta.getBatteryLevel());
        theta.closeSession();
    }

    @Test
    public void getWhiteBalance() throws IOException {
        theta.openSession(SESSION_ID);
        System.out.println("White Balance: " + theta.getWhiteBalance());
        theta.closeSession();
    }

    // Property Setter

    @Test
    public void setWhiteBalance() throws IOException {
        theta.openSession(SESSION_ID);
        theta.setWhiteBalance(new UINT16(0x0004));
        theta.closeSession();
    }
}
