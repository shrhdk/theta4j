package org.theta4j.data;

import org.junit.Test;
import org.theta4j.ptp.type.UINT16;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ISOSpeedTest {
    @Test
    public void value() {
        assertThat(ISOSpeed.AUTO.value(), is(new UINT16(0xFFFF)));
    }
}
