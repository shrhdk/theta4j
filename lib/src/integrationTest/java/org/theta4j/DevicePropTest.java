/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.data.*;
import org.theta4j.ptp.PtpException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class DevicePropTest {
    public static class BatteryLevelTest extends BaseThetaTest {
        @Test
        public void get() throws IOException {
            theta.getBatteryLevel();
        }
    }

    public static class WhiteBalanceTest extends BaseThetaTest {
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

    public static class ExposureIndexTest extends BaseThetaTest {
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

    public static class ExposureBiasCompensationTest extends BaseThetaTest {
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

    public static class DateTimeTest extends BaseThetaTest {
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

    public static class StillCaptureModeTest extends BaseThetaTest {
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

    public static class TimelapseNumberTest extends BaseThetaTest {
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

    public static class TimelapseIntervalTest extends BaseThetaTest {
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

    public static class AudioVolumeTest extends BaseThetaTest {
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

    public static class ErrorInfoTest extends BaseThetaTest {
        @Test
        public void get() throws IOException {
            theta.getErrorInfo();
        }
    }

    public static class ShutterSpeedTest extends BaseThetaTest {
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

    public static class GPSInfoTest extends BaseThetaTest {
        public static Matcher<GPSInfo> almost(final GPSInfo expected) {
            return new TypeSafeMatcher<GPSInfo>() {
                @Override
                public boolean matchesSafely(GPSInfo actual) {
                    return actual != null && actual.almostEquals(expected);
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("almost <" + expected + ">");
                }
            };
        }

        @Test(expected = NullPointerException.class)
        public void setNull() throws IOException {
            theta.setGPSInfo(null);
        }

        @Test
        public void setAndGet() throws IOException {
            GPSInfo given = new GPSInfo(new BigDecimal("35.671190"), new BigDecimal("139.764642"), new BigDecimal("0"), new Date(), TimeZone.getTimeZone("JST").getRawOffset());
            theta.setGPSInfo(given);
            assertThat(theta.getGPSInfo(), is(almost(given)));
        }
    }

    public static class AutoPowerOffDelayTest extends BaseThetaTest {
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

    public static class SleepDelayTest extends BaseThetaTest {
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

    public static class ChannelNumberTest extends BaseThetaTest {
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

    public static class CaptureStatusTest extends BaseThetaTest {
        @Test
        public void get() throws IOException {
            theta.getCaptureStatus();
        }
    }

    public static class RecordingTimeTest extends BaseThetaTest {
        @Test
        public void get() throws IOException {
            theta.getRecordingTime();
        }
    }

    public static class RemainingRecordingTimeTest extends BaseThetaTest {
        @Test
        public void get() throws IOException {
            theta.getRemainingRecordingTime();
        }
    }
}
