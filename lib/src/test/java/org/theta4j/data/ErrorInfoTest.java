/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.junit.Test;
import org.theta4j.ptp.type.UINT32;

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
