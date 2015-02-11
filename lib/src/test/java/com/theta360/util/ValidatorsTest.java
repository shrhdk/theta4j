package com.theta360.util;

import com.theta360.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValidatorsTest {
    // Constructor

    @Test
    public void isUtilClass() {
        assertTrue(TestUtils.isUtilClass(Validators.class));
    }
}
