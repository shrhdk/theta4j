/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class GPSInfoTest {
    private static BigDecimal bd(String nunber) {
        return new BigDecimal(nunber);
    }

    public static Date getDate(int year, int month, int date, int hours, int minutes, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date, hours, minutes, seconds);
        return new Date(cal.getTime().getTime() / 1000 * 1000);
    }

    public static class Constructor {
        @Test(expected = IllegalArgumentException.class)
        public void tooSmallLatitude() {
            new GPSInfo(bd("-90.1"), bd("0"), bd("0"));
        }

        @Test
        public void minLatitude() {
            new GPSInfo(bd("-90.0"), bd("0"), bd("0"));
        }

        @Test
        public void maxLatitude() {
            new GPSInfo(bd("90.0"), bd("0"), bd("0"));
        }

        @Test(expected = IllegalArgumentException.class)
        public void tooLargeLatitude() {
            new GPSInfo(bd("90.1"), bd("0"), bd("0"));
        }

        @Test(expected = IllegalArgumentException.class)
        public void tooSmallLongitude() {
            new GPSInfo(bd("0"), bd("-180.1"), bd("0"));
        }

        @Test
        public void minLongitude() {
            new GPSInfo(bd("0"), bd("-180.0"), bd("0"));
        }

        @Test
        public void maxLongitude() {
            new GPSInfo(bd("0"), bd("180.0"), bd("0"));
        }

        @Test(expected = IllegalArgumentException.class)
        public void tooLargeLongitude() {
            new GPSInfo(bd("0"), bd("180.1"), bd("0"));
        }

        @Test(expected = NullPointerException.class)
        public void withNullDateTime() {
            new GPSInfo(bd("0"), bd("0"), bd("0"), null, TimeZone.getDefault().getRawOffset());
        }
    }

    public static class Parse {
        @Test(expected = ParseException.class)
        public void nonsenseInput() throws ParseException {
            GPSInfo.parse("FOO");
        }

        @Test(expected = ParseException.class)
        public void invalidDatum() throws ParseException {
            GPSInfo.parse("0.000000,0.000000+0.00m@20140102T030405,FOO");
        }

        @Test
        public void zero() throws ParseException {
            String given = "0.000000,0.000000+0.00m@20140102T030405,WGS84";
            GPSInfo expected = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo actual = GPSInfo.parse(given);

            assertThat(actual, is(expected));
        }

        @Test
        public void positiveMax() throws ParseException {
            String given = "90.000000,180.000000+0.00m@20140102T030405,WGS84";
            GPSInfo expected = new GPSInfo(bd("90"), bd("180"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo actual = GPSInfo.parse(given);

            assertThat(actual, is(expected));
        }

        @Test
        public void negativeMax() throws ParseException {
            String given = "-90.000000,-180.000000+0.00m@20140102T030405,WGS84";
            GPSInfo expected = new GPSInfo(bd("-90"), bd("-180"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo actual = GPSInfo.parse(given);

            assertThat(actual, is(expected));
        }

        @Test
        public void omittedAltitude() throws ParseException {
            String given = "90.000000,180.000000+0.0m@20140102T030405,WGS84";
            GPSInfo expected = new GPSInfo(bd("90"), bd("180"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo actual = GPSInfo.parse(given);

            assertThat(actual, is(expected));
        }
    }

    public static class GetTime {
        @Test
        public void fromJST() {
            GPSInfo given = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 9, 4, 5), TimeZone.getTimeZone("GMT+0900").getRawOffset());
            long expected = getDate(2014, 1, 2, 0, 4, 5).getTime();
            long actual = given.getTime();

            assertThat(actual, is(expected));
        }
    }

    public static class ToString {
        @Test
        public void withZero() {
            GPSInfo given = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5), TimeZone.getTimeZone("GMT+0900").getRawOffset());
            String expected = "0.000000,0.000000+0.00m@20140102T030405+0900,WGS84";
            String actual = given.toString();

            assertThat(actual, is(expected));
        }

        @Test
        public void withPositiveMax() {
            GPSInfo given = new GPSInfo(bd("90"), bd("180"), bd("0"), getDate(2014, 1, 2, 3, 4, 5), TimeZone.getTimeZone("GMT+0900").getRawOffset());
            String expected = "90.000000,180.000000+0.00m@20140102T030405+0900,WGS84";
            String actual = given.toString();

            assertThat(actual, is(expected));
        }

        @Test
        public void withNegativeMax() {
            GPSInfo given = new GPSInfo(bd("-90"), bd("-180"), bd("0"), getDate(2014, 1, 2, 3, 4, 5), TimeZone.getTimeZone("GMT+0900").getRawOffset());
            String expected = "-90.000000,-180.000000+0.00m@20140102T030405+0900,WGS84";
            String actual = given.toString();

            assertThat(actual, is(expected));
        }
    }

    public static class NotEquals {
        @Test
        public void withLatitude() {
            GPSInfo gpsInfo1 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo gpsInfo2 = new GPSInfo(bd("1"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));

            assertFalse(gpsInfo1.equals(gpsInfo2));
        }

        @Test
        public void withLongitude() {
            GPSInfo gpsInfo1 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo gpsInfo2 = new GPSInfo(bd("1"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));

            assertFalse(gpsInfo1.equals(gpsInfo2));
        }

        @Test
        public void withAltitude() {
            GPSInfo gpsInfo1 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo gpsInfo2 = new GPSInfo(bd("0"), bd("0"), bd("1"), getDate(2014, 1, 2, 3, 4, 5));

            assertFalse(gpsInfo1.equals(gpsInfo2));
        }

        @Test
        public void withDateTime() {
            GPSInfo gpsInfo1 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5));
            GPSInfo gpsInfo2 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 6));

            assertFalse(gpsInfo1.equals(gpsInfo2));
        }

        @Test
        public void withOffset() {
            GPSInfo gpsInfo1 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5), TimeZone.getTimeZone("GMT+0900").getRawOffset());
            GPSInfo gpsInfo2 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5), TimeZone.getTimeZone("GMT-0700").getRawOffset());

            assertFalse(gpsInfo1.equals(gpsInfo2));
        }
    }

    public static class Equals {
        @Test
        public void withSameValues() {
            GPSInfo gpsInfo1 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5), TimeZone.getTimeZone("GMT+0900").getRawOffset());
            GPSInfo gpsInfo2 = new GPSInfo(bd("0"), bd("0"), bd("0"), getDate(2014, 1, 2, 3, 4, 5), TimeZone.getTimeZone("GMT+0900").getRawOffset());

            assertTrue(gpsInfo1.equals(gpsInfo2));
        }
    }
}
