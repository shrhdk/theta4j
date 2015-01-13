package com.theta360.theta;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import com.theta360.theta.property.WhiteBalance;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException {
        theta = new Theta();
        theta.addListener(listener);
        theta.openSession(SESSION_ID);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        theta.closeSession();
        theta.close();
    }

    // Operations

    @Test
    public void getDeviceInfo() throws IOException {
        theta.getDeviceInfo();
    }

    @Test
    public void getObjectHandles() throws IOException {
        theta.getObjectHandles();
    }

    @Test
    public void getObject() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("raw.jpg"))) {
            theta.getObject(objectHandles.get(2), file);
        }
    }

    @Test
    public void getThumb() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("thumb.jpg"))) {
            theta.getThumb(objectHandles.get(2), file);
        }
    }

    @Test
    public void getResizedImageObject() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("resized.jpg"))) {
            theta.getResizedImageObject(objectHandles.get(2), file);
        }
    }

    // Property Getter

    @Test
    public void getBatteryLevel() throws IOException {
        System.out.println("Battery Level: " + theta.getBatteryLevel());
    }

    @Test
    public void getWhiteBalance() throws IOException {
        System.out.println("White Balance: " + theta.getWhiteBalance());
    }

    @Test
    public void getExposureIndex() throws IOException {
        System.out.println("Exposure Index: " + theta.getExposureIndex());
    }

    @Test
    public void getExposureBiasCompensation() throws IOException {
        System.out.println("Exposure Bias Compensation: " + theta.getExposureIndex());
    }

    @Test
    public void getDateTime() throws IOException {
        System.out.println("DateTime: " + theta.getDateTime());
    }

    @Test
    public void getStillCaptureMode() throws IOException {
        System.out.println("Still Capture Mode: " + theta.getStillCaptureMode());
    }

    @Test
    public void getTimelapseNumber() throws IOException {
        System.out.println("Timelapse Number: " + theta.getTimelapseNumber());
    }

    @Test
    public void getTimelapseInterval() throws IOException {
        System.out.println("Timelapse Interval: " + theta.getTimelapseInterval());
    }

    @Test
    public void getAudioVolume() throws IOException {
        System.out.println("Audio Volume: " + theta.getAudioVolume());
    }

    @Test
    public void getErrorInfo() throws IOException {
        System.out.println("Error Info: " + theta.getErrorInfo());
    }

    @Test
    public void getShutterSpeed() throws IOException {
        System.out.println("Shutter Speed: " + theta.getShutterSpeed());
    }

    @Ignore
    public void getGPSInfo() throws IOException {
        System.out.println("GPS Info: " + theta.getGPSInfo());
    }

    @Test
    public void getAutoPowerOffDelay() throws IOException {
        System.out.println("Auto Power Delay: " + theta.getAutoPowerOffDelay());
    }

    @Test
    public void getSleepDelay() throws IOException {
        System.out.println("Sleep Delay: " + theta.getSleepDelay());
    }

    @Test
    public void getChannelNumber() throws IOException {
        System.out.println("Channel Number: " + theta.getChannelNumber());
    }

    @Test
    public void getCaptureStatus() throws IOException {
        System.out.println("Capture Status: " + theta.getCaptureStatus());
    }

    @Test
    public void getRecordingTime() throws IOException {
        System.out.println("Recording Time: " + theta.getRecordingTime());
    }

    @Test
    public void getRemainingRecordingTime() throws IOException {
        System.out.println("Remaining Recording Time: " + theta.getRemainingRecordingTime());
    }

    // Property Setter

    @Test
    public void setWhiteBalance() throws IOException {
        theta.setWhiteBalance(WhiteBalance.COOL_WHITE_FLUORESCENT_LAMP);
    }
}
