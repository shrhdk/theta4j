package com.theta360.theta.data;

import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ErrorInfoTest {
    @Test
    public void value() {
        assertThat(ErrorInfo.NONE.value(), is(UINT32.ZERO));
    }

    @Test
    public void level() {
        assertThat(ErrorInfo.NONE.level(), is(ErrorInfo.Level.NONE));
    }
}
