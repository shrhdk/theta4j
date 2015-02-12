package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class ResponseTest {
    private static final UINT16 RESPONSE_CODE = new UINT16(0);
    private static final UINT32 SESSION_ID = new UINT32(1);
    private static final UINT32 TRANSACTION_ID = new UINT32(2);
    private static final UINT32 P1 = new UINT32(3);
    private static final UINT32 P2 = new UINT32(4);
    private static final UINT32 P3 = new UINT32(5);
    private static final UINT32 P4 = new UINT32(6);
    private static final UINT32 P5 = new UINT32(7);

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void withNullResponseCode() {
        // act
        new Response(null, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullSessionID() {
        // act
        new Response(RESPONSE_CODE, null, TRANSACTION_ID, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullTransactionID() {
        // act
        new Response(RESPONSE_CODE, SESSION_ID, null, P1, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP1() {
        // act
        new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, null, P2, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP2() {
        // act
        new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, null, P3, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP3() {
        // act
        new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, null, P4, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP4() {
        // act
        new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, null, P5);
    }

    @Test(expected = NullPointerException.class)
    public void withNullP5() {
        // act
        new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, null);
    }

    // Construct

    @Test
    public void constructAndGet() {
        // act
        Response actual = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // verify
        assertThat(actual.getResponseCode(), is(RESPONSE_CODE));
        assertThat(actual.getSessionID(), is(SESSION_ID));
        assertThat(actual.getTransactionID(), is(TRANSACTION_ID));
        assertThat(actual.getP1(), is(P1));
        assertThat(actual.getP2(), is(P2));
        assertThat(actual.getP3(), is(P3));
        assertThat(actual.getP4(), is(P4));
        assertThat(actual.getP5(), is(P5));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentResponseCode() {
        // given
        Response response1 = new Response(new UINT16(0), SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);
        Response response2 = new Response(new UINT16(1), SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentSessionID() {
        // given
        Response response1 = new Response(RESPONSE_CODE, new UINT32(0), TRANSACTION_ID, P1, P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, new UINT32(1), TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentTransactionID() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, new UINT32(0), P1, P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, new UINT32(1), P1, P2, P3, P4, P5);

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP1() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(0), P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(1), P2, P3, P4, P5);

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP2() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(0), P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(1), P3, P4, P5);

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP3() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(0), P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(1), P4, P5);

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP4() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, new UINT32(0), P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, new UINT32(1), P5);

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentP5() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, new UINT32(0));
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, new UINT32(1));

        // act & verify
        assertThat(response1.hashCode(), not(response2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertThat(response1.hashCode(), is(response2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        Response response = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertFalse(response.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        Response response = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertFalse(response.equals("foo"));
    }

    @Test
    public void notEqualsWithResponseCode() {
        // given
        Response response1 = new Response(new UINT16(0), SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);
        Response response2 = new Response(new UINT16(1), SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertFalse(response1.equals(response2));
    }

    @Test
    public void notEqualsWithSessionID() {
        // given
        Response response1 = new Response(RESPONSE_CODE, new UINT32(0), TRANSACTION_ID, P1, P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, new UINT32(1), TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertFalse(response1.equals(response2));
    }

    @Test
    public void notEqualsWithTransactionID() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, new UINT32(0), P1, P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, new UINT32(1), P1, P2, P3, P4, P5);

        // act & verify
        assertFalse(response1.equals(response2));
    }

    @Test
    public void notEqualsWithP1() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(0), P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, new UINT32(1), P2, P3, P4, P5);

        // act & verify
        assertFalse(response1.equals(response2));
    }

    @Test
    public void notEqualsWithP2() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(0), P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, new UINT32(1), P3, P4, P5);

        // act & verify
        assertFalse(response1.equals(response2));
    }

    @Test
    public void notEqualsWithP3() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(0), P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, new UINT32(1), P4, P5);

        // act & verify
        assertFalse(response1.equals(response2));
    }

    @Test
    public void notEqualsWithP4() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, new UINT32(0), P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, new UINT32(1), P5);

        // act & verify
        assertFalse(response1.equals(response2));
    }

    @Test
    public void notEqualsWithP5() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, new UINT32(0));
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, new UINT32(1));

        // act & verify
        assertFalse(response1.equals(response2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        Response response = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertTrue(response.equals(response));
    }

    @Test
    public void equals() {
        // given
        Response response1 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);
        Response response2 = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act & verify
        assertTrue(response1.equals(response2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        Response response = new Response(RESPONSE_CODE, SESSION_ID, TRANSACTION_ID, P1, P2, P3, P4, P5);

        // act
        String actual = response.toString();

        // verify
        assertTrue(actual.contains(Response.class.getSimpleName()));
        assertTrue(actual.contains("responseCode"));
        assertTrue(actual.contains("sessionID"));
        assertTrue(actual.contains("transactionID"));
        assertTrue(actual.contains("p1"));
        assertTrue(actual.contains("p2"));
        assertTrue(actual.contains("p3"));
        assertTrue(actual.contains("p4"));
        assertTrue(actual.contains("p5"));
    }
}
