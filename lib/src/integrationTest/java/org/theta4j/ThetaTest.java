/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.Test;

import java.io.IOException;

public class ThetaTest extends BaseThetaTest {
    @Test
    public void getDeviceInfo() throws IOException {
        theta.getDeviceInfo();
    }
}
