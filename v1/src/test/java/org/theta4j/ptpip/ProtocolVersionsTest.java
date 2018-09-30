/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.TestUtils;

import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class ProtocolVersionsTest {
    public static class Design {
        @Test
        public void isUtilClass() throws Throwable {
            assertTrue(TestUtils.isUtilClass(ProtocolVersions.class));
        }
    }
}
