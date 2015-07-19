/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.data.*;
import org.theta4j.ptp.PtpException;

import java.io.IOException;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class DevicePropTest {
    private static Theta theta;

    static {
        try {
            theta = new Theta();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: fix workaround
    private static void changeStillCaptureModeFast(StillCaptureMode mode) throws IOException {
        StillCaptureMode current = theta.getStillCaptureMode();

        if (current == mode) {
            return;
        }

        theta.setStillCaptureMode(mode);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void connect() throws IOException {

    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    public static class BatteryLevelTest {
        @Test
        public void get() throws IOException {
            theta.getBatteryLevel();
        }
    }

    public static class WhiteBalanceTest {
        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setWhiteBalance(null);
        }

        @Test
        public void setAndGetAllValues() throws IOException {
            for (WhiteBalance given : WhiteBalance.values()) {
                theta.setWhiteBalance(given);
                assertThat(theta.getWhiteBalance(), is(given));
            }
        }
    }

    public static class ExposureIndexTest {
        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setExposureIndex(null);
        }

        @Test
        public void setAndGetAllValues() throws IOException {
            for (ISOSpeed given : ISOSpeed.values()) {
                theta.setExposureIndex(given);
                assertThat(theta.getExposureIndex(), is(given));
            }
        }
    }

    public static class ExposureBiasCompensationTest {
        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setExposureBiasCompensation(null);
        }

        @Test
        public void setAndGetAllValues() throws IOException {
            for (ExposureBiasCompensation given : ExposureBiasCompensation.values()) {
                theta.setExposureBiasCompensation(given);
                assertThat(theta.getExposureBiasCompensation(), is(given));
            }
        }
    }

    public static class DateTimeTest {
        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setDateTime(null);
        }

        @Test
        public void setAndGet() throws IOException {
            Date given = new Date();
            theta.setDateTime(given);
            assertThat(theta.getDateTime().toString(), is(given.toString()));
        }
    }

    public static class StillCaptureModeTest {
        public static void setAndGetAndVerify(StillCaptureMode given) throws IOException {
            theta.setStillCaptureMode(given);
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            assertThat(theta.getStillCaptureMode(), is(given));
        }

        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setStillCaptureMode(null);
        }

        @Test
        public void setAndGetSingle() throws IOException {
            setAndGetAndVerify(StillCaptureMode.SINGLE);
        }

        @Test
        public void setAndGetTimeLapse() throws IOException {
            setAndGetAndVerify(StillCaptureMode.TIME_LAPSE);
        }
    }

    public static class TimelapseNumberTest {
        public static void setAndGetAndVerify(int given) throws IOException {
            changeStillCaptureModeFast(StillCaptureMode.SINGLE);
            theta.setTimelapseNumber(given);
            assertThat(theta.getTimelapseNumber(), is(given));
        }

        @Test(expected = IllegalArgumentException.class)
        public void setNegativeNumber() throws IOException {
            theta.setTimelapseNumber(-1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void set1() throws IOException {
            theta.setTimelapseNumber(-1);
        }

        @Test(expected = PtpException.class)
        public void setValueWhileIntervalMode() throws IOException {
            changeStillCaptureModeFast(StillCaptureMode.TIME_LAPSE);
            theta.setTimelapseNumber(2);
        }

        @Test
        public void setAndGetUnlimited() throws IOException {
            setAndGetAndVerify(0);
        }

        @Test
        public void setAndGet2() throws IOException {
            setAndGetAndVerify(2);
        }

        @Test
        public void setAndGetMaxNumber() throws IOException {
            setAndGetAndVerify(65535);
        }
    }

    public static class TimelapseIntervalTest {
        public static void setAndGetAndVerify(int given) throws IOException {
            changeStillCaptureModeFast(StillCaptureMode.SINGLE);
            theta.setTimelapseInterval(given);
            assertThat(theta.getTimelapseInterval(), is(given));
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooSmallValue() throws IOException {
            theta.setTimelapseInterval(4999);
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooBigValue() throws IOException {
            theta.setTimelapseInterval(3600001);
        }

        @Test(expected = PtpException.class)
        public void setValueWhileIntervalMode() throws IOException {
            changeStillCaptureModeFast(StillCaptureMode.TIME_LAPSE);
            theta.setTimelapseInterval(5000);
        }

        @Test
        public void setAndGetMinNumber() throws IOException {
            setAndGetAndVerify(5000);
        }

        @Test
        public void setAndGetMaxNumber() throws IOException {
            setAndGetAndVerify(3600000);
        }
    }

    public static class AudioVolumeTest {
        public static void setAndGetAndVerify(int given) throws IOException {
            theta.setAudioVolume(given);
            assertThat(theta.getAudioVolume(), is(given));
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooSmallValue() throws IOException {
            theta.setAudioVolume(-1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooBigValue() throws IOException {
            theta.setAudioVolume(101);
        }

        @Test
        public void setAndGetMinNumber() throws IOException {
            setAndGetAndVerify(0);
        }

        @Test
        public void setAndGetMaxNumber() throws IOException {
            setAndGetAndVerify(100);
        }
    }

    public static class ErrorInfoTest {
        @Test
        public void get() throws IOException {
            theta.getErrorInfo();
        }
    }

    public static class ShutterSpeedTest {
        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setShutterSpeed(null);
        }

        @Test
        public void setAndGetAllValues() throws IOException {
            for (ShutterSpeed given : ShutterSpeed.values()) {
                theta.setShutterSpeed(given);
                assertThat(theta.getShutterSpeed(), is(given));
            }
        }
    }

    @Ignore
    public static class GPSInfoTest {
        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setGPSInfo(null);
        }

        @Test
        public void setAndGet() throws IOException {
            String given = "35.671190,139.764642+000.00m@19630103T000000+0900,WGS84";
            theta.setGPSInfo(given);
            assertThat(theta.getGPSInfo(), is(given));
        }
    }

    public static class AutoPowerOffDelayTest {
        public static void setAndGetAndVerify(int given) throws IOException {
            theta.setAutoPowerOffDelay(given);
            assertThat(theta.getAutoPowerOffDelay(), is(given));
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooSmallValue() throws IOException {
            theta.setAutoPowerOffDelay(-1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooBigValue() throws IOException {
            theta.setAutoPowerOffDelay(31);
        }

        @Test
        public void setAndGetMinNumber() throws IOException {
            setAndGetAndVerify(0);
        }

        @Test
        public void setAndGetMaxNumber() throws IOException {
            setAndGetAndVerify(30);
        }
    }

    public static class SleepDelayTest {
        public static void setAndGetAndVerify(int given) throws IOException {
            theta.setSleepDelay(given);
            assertThat(theta.getSleepDelay(), is(given));
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooSmallValue() throws IOException {
            theta.setSleepDelay(-1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void setTooBigValue() throws IOException {
            theta.setSleepDelay(1801);
        }

        @Test
        public void setAndGetMinNumber() throws IOException {
            setAndGetAndVerify(0);
        }

        @Test
        public void setAndGetMaxNumber() throws IOException {
            setAndGetAndVerify(1800);
        }
    }

    public static class ChannelNumberTest {
        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setChannelNumber(null);
        }

        @Test
        public void setAndGetAllValues() throws IOException {
            for (ChannelNumber given : ChannelNumber.values()) {
                theta.setChannelNumber(given);
                assertThat(theta.getChannelNumber(), is(given));
            }
        }
    }

    public static class CaptureStatusTest {
        @Test
        public void get() throws IOException {
            theta.getCaptureStatus();
        }
    }

    public static class RecordingTimeTest {
        @Test
        public void get() throws IOException {
            theta.getRecordingTime();
        }
    }

    public static class RemainingRecordingTimeTest {
        @Test
        public void get() throws IOException {
            theta.getRemainingRecordingTime();
        }
    }
}
