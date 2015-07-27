/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.theta4j.util.Validators;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GPSInfo {
    private static final String DATUM = "WGS84";
    private static final String FORMAT_DATE_TIME = "yyyyMMdd'T'HHmmss";

    private static final String PATTERN_LATITUDE = "-?\\d{1,2}\\.\\d{6}";
    private static final String PATTERN_LONGITUDE = "-?\\d{1,3}\\.\\d{6}";
    private static final String PATTERN_ALTITUDE = "[+|-]\\d{1,3}\\.\\d{1,2}";

    private static final String PATTERN_DATE = "\\d{8}";
    private static final String PATTERN_TIME = "\\d{6}(?:\\.\\d{1})?";
    private static final String PATTERN_DATE_TIME = String.format("%sT%s", PATTERN_DATE, PATTERN_TIME);

    private static final Pattern PATTERN_GPS_INFO = Pattern.compile(
            String.format("(%s),(%s)(%s)m@(%s),(%s)", PATTERN_LATITUDE, PATTERN_LONGITUDE, PATTERN_ALTITUDE, PATTERN_DATE_TIME, DATUM));

    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final BigDecimal altitude;
    private final Date dateTime;
    private final int offset;

    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    // WGS84 Constants

    public static final BigDecimal LATITUDE_MIN = new BigDecimal("-90.000000");
    public static final BigDecimal LATITUDE_MAX = new BigDecimal("90.000000");
    public static final BigDecimal LONGITUDE_MIN = new BigDecimal("-180.000000");
    public static final BigDecimal LONGITUDE_MAX = new BigDecimal("180.000000");

    // Constructor

    public GPSInfo(BigDecimal latitude, BigDecimal longitude, BigDecimal altitude) {
        this(latitude, longitude, altitude, new Date());
    }

    public GPSInfo(BigDecimal latitude, BigDecimal longitude, BigDecimal altitude, Date dateTime) {
        this(latitude, longitude, altitude, dateTime, 0);
    }

    public GPSInfo(BigDecimal latitude, BigDecimal longitude, BigDecimal altitude, Date dateTime, int offset) {
        Validators.rangeEq("latitude", latitude, LATITUDE_MIN, LATITUDE_MAX);
        Validators.rangeEq("longitude", longitude, LONGITUDE_MIN, LONGITUDE_MAX);
        Validators.notNull("dateTime", dateTime);

        this.latitude = latitude.setScale(6, ROUNDING_MODE);
        this.longitude = longitude.setScale(6, ROUNDING_MODE);
        this.altitude = altitude.setScale(2, ROUNDING_MODE);
        this.dateTime = truncate(dateTime);
        this.offset = truncate(offset);
    }

    // Static Factory Method

    public static GPSInfo parse(String str) throws ParseException {
        Matcher m = PATTERN_GPS_INFO.matcher(str);
        if (!m.find()) {
            throw new ParseException(str, 0);
        }

        BigDecimal latitude = new BigDecimal(m.group(1)).setScale(6, ROUNDING_MODE);
        BigDecimal longitude = new BigDecimal(m.group(2)).setScale(6, ROUNDING_MODE);
        BigDecimal altitude = new BigDecimal(m.group(3)).setScale(2, ROUNDING_MODE);
        Date dateTime = new SimpleDateFormat(FORMAT_DATE_TIME).parse(m.group(4));
        String datum = m.group(5);

        if (LATITUDE_MIN.compareTo(latitude) == 1 || LATITUDE_MAX.compareTo(latitude) == -1) {
            throw new ParseException("Expected: -90 <= latitude <= 90, but was: " + str, m.start(1));
        }

        if (LONGITUDE_MIN.compareTo(longitude) == 1 || LONGITUDE_MAX.compareTo(longitude) == -1) {
            throw new ParseException("Expected: -180 <= latitude <= 180, but was: " + str, m.start(2));
        }

        if (dateTime == null) {
            throw new ParseException("Failed to parse date time part: " + str, m.start(4));
        }

        if (!datum.equals(DATUM)) {
            throw new ParseException("DATUM part must be WGS84: " + str, m.start(5));
        }

        return new GPSInfo(latitude, longitude, altitude, dateTime);
    }

    // Getter

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getAltitude() {
        return altitude;
    }

    public Date getDateTime() {
        return (Date) dateTime.clone();
    }

    public int getOffset() {
        return offset;
    }

    public long getTime() {
        return DateUtils.addMilliseconds(dateTime, -offset).getTime();
    }

    // toString

    @Override
    public String toString() {
        if (offset == 0) {
            return String.format("%sm@%s,%s", toCoordinateString(), toDateTimeString(), DATUM);
        } else {
            return String.format("%sm@%s%s,%s", toCoordinateString(), toDateTimeString(), toOffsetString(), DATUM);
        }
    }

    private String toCoordinateString() {
        return String.format("%2.6f,%3.6f%+3.2f", latitude, longitude, altitude);
    }

    private String toDateTimeString() {
        return new SimpleDateFormat(FORMAT_DATE_TIME).format(dateTime);
    }

    private String toOffsetString() {
        int offsetMinutes = offset / 1000 / 60 / 60;
        int offsetSeconds = offset / 1000 / 60 % 60;
        return String.format("+%02d%02d", offsetMinutes, offsetSeconds);
    }

    // Basic Method

    public boolean almostEquals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GPSInfo gpsInfo = (GPSInfo) o;

        return new EqualsBuilder()
                .append(latitude.setScale(3, ROUNDING_MODE), gpsInfo.latitude.setScale(3, ROUNDING_MODE))
                .append(longitude.setScale(3, ROUNDING_MODE), gpsInfo.longitude.setScale(3, ROUNDING_MODE))
                .append(altitude.setScale(1, ROUNDING_MODE), gpsInfo.altitude.setScale(1, ROUNDING_MODE))
                .append(getTime(), gpsInfo.getTime())
                .isEquals();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GPSInfo gpsInfo = (GPSInfo) o;

        return new EqualsBuilder()
                .append(latitude, gpsInfo.latitude)
                .append(longitude, gpsInfo.longitude)
                .append(altitude, gpsInfo.altitude)
                .append(getTime(), gpsInfo.getTime())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(latitude)
                .append(longitude)
                .append(altitude)
                .append(getTime())
                .toHashCode();
    }

    // Helper

    private static int truncate(int milliSec) {
        return milliSec / 1000 * 1000;
    }

    private static Date truncate(Date date) {
        return new Date(date.getTime() / 1000 * 1000);
    }
}
