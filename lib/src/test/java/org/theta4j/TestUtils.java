/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class TestUtils {
    private TestUtils() {
        throw new AssertionError();
    }

    public static boolean isUtilClass(Class<?> class_) {
        // It has only one constructor.
        int numOfConstructor = class_.getDeclaredConstructors().length;
        if (1 != numOfConstructor) {
            return false;
        }

        // Get Constructor
        Constructor<?> constructor;
        try {
            constructor = class_.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            return false;
        }

        boolean constructorIsPrivate = Modifier.isPrivate(constructor.getModifiers());
        if (!constructorIsPrivate) {
            return false;
        }

        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return false;
        } catch (InvocationTargetException e) {
            return e.getTargetException() instanceof AssertionError;
        }

        return false;
    }
}
