package org.theta4j.ptpip.packet;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.TestUtils;

import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class PtpIpPacketUtilsTest {
    public static class Design {
        @Test
        public void isUtilClass() {
            assertTrue(TestUtils.isUtilClass(PtpIpPacketUtils.class));
        }
    }
}
