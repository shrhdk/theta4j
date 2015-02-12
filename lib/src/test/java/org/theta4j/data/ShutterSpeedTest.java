package org.theta4j.data;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShutterSpeedTest {
    @Test
    public void value() {
        assertThat(ShutterSpeed.SS_1_8000.value(), is(new Rational(1, 8000)));
    }
}
