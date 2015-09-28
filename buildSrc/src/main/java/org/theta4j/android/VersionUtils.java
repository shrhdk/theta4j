/*
 * Copyright (C) 2015 theta4j project
 */
package org.theta4j.android;

import com.github.zafarkhaja.semver.Version;

public class VersionUtils {
    private static final int BIT_PER_NUM = 10;
    private static final int MAX = (1 << BIT_PER_NUM) - 1;

    private VersionUtils() {
        throw new AssertionError();
    }

    public static int semverToInt(Object object) {
        return semverToInt(object.toString());
    }

    public static int semverToInt(String semver) {
        final Version ver = Version.valueOf(semver);
        final int major = ver.getMajorVersion();
        final int minor = ver.getMinorVersion();
        final int patch = ver.getPatchVersion();

        if (MAX < major || MAX < minor || MAX < patch) {
            final String message = String.format("Each number in version name must be %d or less, but given is %s.", MAX, semver);
            throw new IllegalArgumentException(message);
        }

        return major << BIT_PER_NUM * 2 | minor << BIT_PER_NUM * 1 | patch << BIT_PER_NUM * 0;
    }
}
