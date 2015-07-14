/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.code;

import org.junit.Test;
import org.theta4j.ptp.type.UINT16;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DevicePropCodeTest {
    @Test
    public void value() {
        assertThat(DevicePropCode.UNDEFINED.value(), is(new UINT16(0x5000)));
    }
}
