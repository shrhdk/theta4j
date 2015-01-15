package com.theta360.data;

import com.theta360.ptp.data.TransactionID;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransactionIDTest {
    @Test
    public void iterate() {
        TransactionID transactionID = new TransactionID();

        for (long i = 0; i < 3; i++) {
            assertThat(transactionID.next().longValue(), is(i));
        }
    }

    @Test
    public void loop() throws NoSuchFieldException, IllegalAccessException {
        TransactionID transactionID = new TransactionID();

        Field current = TransactionID.class.getDeclaredField("current");
        current.setAccessible(true);
        current.set(transactionID, 0xFFFF_FFFEL);

        assertThat(transactionID.next().longValue(), is(0xFFFF_FFFEL));
        // Skip 0xFFFF_FFFFL because that is reserved.
        assertThat(transactionID.next().longValue(), is(0L));
    }
}
