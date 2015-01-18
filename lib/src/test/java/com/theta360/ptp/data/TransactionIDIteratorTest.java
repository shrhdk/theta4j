package com.theta360.ptp.data;

import com.theta360.ptp.TransactionIDIterator;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransactionIDIteratorTest {
    @Test
    public void iterate() {
        TransactionIDIterator transactionIDIterator = new TransactionIDIterator();

        for (long i = 0; i < 3; i++) {
            assertThat(transactionIDIterator.next().longValue(), is(i));
        }
    }

    @Test
    public void loop() throws NoSuchFieldException, IllegalAccessException {
        TransactionIDIterator transactionIDIterator = new TransactionIDIterator();

        Field current = TransactionIDIterator.class.getDeclaredField("current");
        current.setAccessible(true);
        current.set(transactionIDIterator, 0xFFFF_FFFEL);

        assertThat(transactionIDIterator.next().longValue(), is(0xFFFF_FFFEL));
        // Skip 0xFFFF_FFFFL because that is reserved.
        assertThat(transactionIDIterator.next().longValue(), is(0L));
    }
}
