package com.theta360.theta.property;

import java.util.Date;
import java.util.TimeZone;

// TODO Implement
public class GPSInfo {
    private final double latitude;
    private final double longitude;
    private final double altitude;
    private final Date dateTime;
    private final TimeZone timeZone;
    private static final String DATUM = "WGS84";

    // Constructor

    public GPSInfo(double latitude, double longitude, double altitude, Date dateTime, TimeZone timeZone) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.dateTime = (Date) dateTime.clone();
        this.timeZone = (TimeZone) timeZone.clone();
    }

    // Getter

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public Date getDateTime() {
        return (Date) dateTime.clone();
    }

    public TimeZone getTimeZone() {
        return (TimeZone) timeZone.clone();
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPSInfo gpsInfo = (GPSInfo) o;

        if (Double.compare(gpsInfo.altitude, altitude) != 0) return false;
        if (Double.compare(gpsInfo.latitude, latitude) != 0) return false;
        if (Double.compare(gpsInfo.longitude, longitude) != 0) return false;
        if (!dateTime.equals(gpsInfo.dateTime)) return false;
        if (!timeZone.equals(gpsInfo.timeZone)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(altitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + dateTime.hashCode();
        result = 31 * result + timeZone.hashCode();
        return result;
    }

    @Override
    public String toString() {
        // TODO Implement
        // String dateTimeString = new SimpleDateFormat("").format(dateTime);
        // String timeZoneString = "";

        throw new UnsupportedOperationException();
        // return String.format("%2.6f,%2.6f%+3.2fm@%s%s,%s", latitude, longitude, altitude, dateTimeString, timeZoneString, DATUM);
    }

    // Static Factory Method

    public static GPSInfo parse(String str) {
        // TODO Implement
        throw new UnsupportedOperationException();
    }
}
