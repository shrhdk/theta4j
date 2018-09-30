/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.junit.Before;
import org.junit.Test;
import org.theta4j.ptp.code.EventCode;
import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.type.UINT32;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PtpEventListenerSetTest {
    private static final UINT32 SESSION_ID = new UINT32(0);
    private static final UINT32 TRANSACTION_ID = new UINT32(1);

    private PtpEventListenerSet set;
    private PtpEventListener listener1, listener2;

    // Set up

    @Before
    public void setUp() {
        set = new PtpEventListenerSet();
        listener1 = mock(PtpEventListener.class);
        listener2 = mock(PtpEventListener.class);

        set.add(listener1);
        set.add(listener2);
    }

    // Set

    @Test
    public void remove() {
        assertThat(set.remove(mock(PtpEventListener.class)), is(false));
        assertThat(set.size(), is(2));

        set.remove(listener1);
        assertThat(set.size(), is(1));

        set.remove(listener2);
        assertThat(set.size(), is(0));
    }

    @Test
    public void clear() {
        // act
        set.clear();

        // verify
        assertThat(set.size(), is(0));
    }

    @Test
    public void iterator() {
        // verify
        assertThat(set.iterator(), notNullValue());
    }

    @Test
    public void size() {
        // verify
        assertThat(set.size(), is(2));
    }

    // onEvent

    @Test
    public void onEvent() {
        // given
        Event given = new Event(EventCode.UNDEFINED.value(), SESSION_ID, TRANSACTION_ID);

        // act
        set.onEvent(given);

        // verify
        verify(listener1).onEvent(given);
        verify(listener2).onEvent(given);
    }
}
