/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransactionIDIteratorTest {
    @Test(expected = UnsupportedOperationException.class)
    public void removeIsNotSupported() {
        // given
        TransactionIDIterator given = new TransactionIDIterator();

        // act
        given.remove();
    }

    @Test
    public void iterate() {
        TransactionIDIterator iterator = new TransactionIDIterator();

        for (long i = 0; i < 3; i++) {
            assertThat(iterator.hasNext(), is(true));
            assertThat(iterator.next().longValue(), is(i));
        }
    }

    @Test
    public void loop() throws NoSuchFieldException, IllegalAccessException {
        TransactionIDIterator iterator = new TransactionIDIterator();

        Field current = TransactionIDIterator.class.getDeclaredField("current");
        current.setAccessible(true);
        current.set(iterator, 0xFFFF_FFFEL);

        assertThat(iterator.next().longValue(), is(0xFFFF_FFFEL));
        // Skip 0xFFFF_FFFFL because that is reserved.
        assertThat(iterator.next().longValue(), is(0L));
    }
}
