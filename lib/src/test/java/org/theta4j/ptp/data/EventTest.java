package org.theta4j.ptp.data;

import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class EventTest {
    private static final UINT16 EVENT_CODE = new UINT16(0);
    private static final UINT32 SESSION_ID = new UINT32(1);
    private static final UINT32 TRANSACTION_ID = new UINT32(2);
    private static final UINT32 P1 = new UINT32(3);
    private static final UINT32 P2 = new UINT32(4);
    private static final UINT32 P3 = new UINT32(5);

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void withNullEventCode() {
        // act
        new Event(null, SESSION_ID, TRANSACTION_ID, P1, P2, P3);
    }

    @Test(expected = NullPointerException.class)
    public void withNullSessionID() {
        // act
        new Event(EVENT_CODE, null, TRANSACTION_ID, P1, P2, P3);
    }

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new Event(EVENT_CODE, SESSION_ID, null, P1, P2, P3);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP1() {
        // act
        new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, null, P2, P3);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP2() {
        // act
        new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, null, P3);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP3() {
        // act
        new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, null);
    }

    // Construct

    @Test
    public void constructAndGet() {
        // act
        Event actual = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID);

        // verify
        assertThat(actual.getEventCode(), is(EVENT_CODE));
        assertThat(actual.getSessionID(), is(SESSION_ID));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getP1(), is(UINT32.ZERO));
        assertThat(actual.getP2(), is(UINT32.ZERO));
        assertThat(actual.getP3(), is(UINT32.ZERO));
    }

    @Test
    public void constructAndGetWithP1() {
        // act
        Event actual = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1);

        // verify
        assertThat(actual.getEventCode(), is(EVENT_CODE));
        assertThat(actual.getSessionID(), is(SESSION_ID));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getP1(), is(P1));
        assertThat(actual.getP2(), is(UINT32.ZERO));
        assertThat(actual.getP3(), is(UINT32.ZERO));
    }

    @Test
    public void constructAndGetWithP12() {
        // act
        Event actual = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2);

        // verify
        assertThat(actual.getEventCode(), is(EVENT_CODE));
        assertThat(actual.getSessionID(), is(SESSION_ID));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getP1(), is(P1));
        assertThat(actual.getP2(), is(P2));
        assertThat(actual.getP3(), is(UINT32.ZERO));
    }

    @Test
    public void constructAndGetWithP123() {
        // act
        Event actual = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // verify
        assertThat(actual.getEventCode(), is(EVENT_CODE));
        assertThat(actual.getSessionID(), is(SESSION_ID));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getP1(), is(P1));
        assertThat(actual.getP2(), is(P2));
        assertThat(actual.getP3(), is(P3));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentEventCode() {
        // given
        Event event1 = new Event(new UINT16(0), SESSION_ID, TRANSACTION_ID, P1, P2, P3);
        Event event2 = new Event(new UINT16(1), SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertThat(event1.hashCode(), not(event2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentSessionID() {
        // given
        Event event1 = new Event(EVENT_CODE, new UINT32(0), TRANSACTION_ID, P1, P2, P3);
        Event event2 = new Event(EVENT_CODE, new UINT32(1), TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertThat(event1.hashCode(), not(event2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, new UINT32(0), P1, P2, P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, new UINT32(1), P1, P2, P3);

        // act & verify
        assertThat(event1.hashCode(), not(event2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP1() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(0), P2, P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(1), P2, P3);

        // act & verify
        assertThat(event1.hashCode(), not(event2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP2() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(0), P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(1), P3);

        // act & verify
        assertThat(event1.hashCode(), not(event2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP3() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(0));
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(1));

        // act & verify
        assertThat(event1.hashCode(), not(event2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertThat(event1.hashCode(), is(event2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        Event event = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertFalse(event.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        Event event = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertFalse(event.equals("foo"));
    }

    @Test
    public void notEqualsWithEventCode() {
        // given
        Event event1 = new Event(new UINT16(0), SESSION_ID, TRANSACTION_ID, P1, P2, P3);
        Event event2 = new Event(new UINT16(1), SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertFalse(event1.equals(event2));
    }

    @Test
    public void notEqualsWithSessionID() {
        // given
        Event event1 = new Event(EVENT_CODE, new UINT32(0), TRANSACTION_ID, P1, P2, P3);
        Event event2 = new Event(EVENT_CODE, new UINT32(1), TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertFalse(event1.equals(event2));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, new UINT32(0), P1, P2, P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, new UINT32(1), P1, P2, P3);

        // act & verify
        assertFalse(event1.equals(event2));
    }

    @Test
    public void notEqualsWithP1() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(0), P2, P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(1), P2, P3);

        // act & verify
        assertFalse(event1.equals(event2));
    }

    @Test
    public void notEqualsWithP2() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(0), P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(1), P3);

        // act & verify
        assertFalse(event1.equals(event2));
    }

    @Test
    public void notEqualsWithP3() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(0));
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(1));

        // act & verify
        assertFalse(event1.equals(event2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        Event event = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertTrue(event.equals(event));
    }

    @Test
    public void equals() {
        // given
        Event event1 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);
        Event event2 = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act & verify
        assertTrue(event1.equals(event2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        Event event = new Event(EVENT_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3);

        // act
        String actual = event.toString();

        // verify
        assertTrue(actual.contains(Event.class.getSimpleName()));
        assertTrue(actual.contains("eventCode"));
        assertTrue(actual.contains("sessionID"));
        assertTrue(actual.contains("transactionID"));
        assertTrue(actual.contains("p1"));
        assertTrue(actual.contains("p2"));
        assertTrue(actual.contains("p3"));
    }
}
