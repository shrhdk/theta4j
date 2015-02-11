package com.theta360.theta.data;

import com.theta360.ptp.type.UINT16;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ISOSpeedTest {
    @Test
    public void value() {
        assertThat(ISOSpeed.AUTO.value(), is(new UINT16(0xFFFF)));
    }
}
