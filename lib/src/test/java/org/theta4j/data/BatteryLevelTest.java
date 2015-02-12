package org.theta4j.data;

import org.theta4j.ptp.type.UINT8;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BatteryLevelTest {
    @Test
    public void value() {
        assertThat(BatteryLevel.END.value(), is(UINT8.ZERO));
    }
}
