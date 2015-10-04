/*
 * Copyright (C) 2015 theta4j project
 */
package org.theta4j.android;

public final class Version {
    private static final int BIT_PER_NUM = 10;
    private static final int MAX = (1 << BIT_PER_NUM) - 1;

    private final String name;
    private final int code;

    private Version(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static Version parse(String semver) {
        final com.github.zafarkhaja.semver.Version ver = com.github.zafarkhaja.semver.Version.valueOf(semver);
        final int major = ver.getMajorVersion();
        final int minor = ver.getMinorVersion();
        final int patch = ver.getPatchVersion();

        if (MAX < major || MAX < minor || MAX < patch) {
            final String message = String.format("Each number in version name must be %d or less, but given is %s.", MAX, semver);
            throw new IllegalArgumentException(message);
        }

        final int code = major << BIT_PER_NUM * 2 | minor << BIT_PER_NUM * 1 | patch << BIT_PER_NUM * 0;

        return new Version(semver, code);
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
