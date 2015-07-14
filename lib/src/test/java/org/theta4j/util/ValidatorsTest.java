/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.util;

import org.junit.Test;
import org.theta4j.TestUtils;

import static org.junit.Assert.assertTrue;

public class ValidatorsTest {
    // Constructor

    @Test
    public void isUtilClass() {
        assertTrue(TestUtils.isUtilClass(Validators.class));
    }
}
