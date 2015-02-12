package org.theta4j.util;

import org.theta4j.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValidatorsTest {
    // Constructor

    @Test
    public void isUtilClass() {
        assertTrue(TestUtils.isUtilClass(Validators.class));
    }
}
