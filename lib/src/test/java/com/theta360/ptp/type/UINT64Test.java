package com.theta360.ptp.type;

import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@Category(UnitTest.class)
public class UINT64Test {
    private static final UINT64 V1 = new UINT64(0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00);
    private static final UINT64 V2 = new UINT64(0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00);
    private static final UINT64 V3 = new UINT64(0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00);

    // Constructor

    @Test
    public void constructAndGet() {
        // verify
        assertThat(V1.bytes(), is(new byte[]{0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}));
        assertThat(V1.bigInteger(), is(new BigInteger("1")));

        // verify
        assertThat(V2.bytes(), is(new byte[]{0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}));
        assertThat(V2.bigInteger(), is(new BigInteger("2")));

        // verify
        assertThat(V3.bytes(), is(new byte[]{0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}));
        assertThat(V3.bigInteger(), is(new BigInteger("3")));
    }

    // Basic method

    @Test
    public void compare() {
        // verify
        assertThat(V1.compareTo(V1), is(0));
        assertThat(V1.compareTo(V2), is(-1));
        assertThat(V1.compareTo(V3), is(-1));

        // verify
        assertThat(V2.compareTo(V1), is(1));
        assertThat(V2.compareTo(V2), is(0));
        assertThat(V2.compareTo(V3), is(-1));

        // verify
        assertThat(V3.compareTo(V1), is(1));
        assertThat(V3.compareTo(V2), is(1));
        assertThat(V3.compareTo(V3), is(0));
    }

    @Test
    public void testHashCode() {
        // verify
        assertThat(V1.hashCode(), is(V1.hashCode()));
        assertThat(V1.hashCode(), is(not(V2.hashCode())));
        assertThat(V1.hashCode(), is(not(V3.hashCode())));

        // verify
        assertThat(V2.hashCode(), is(not(V1.hashCode())));
        assertThat(V2.hashCode(), is(V2.hashCode()));
        assertThat(V2.hashCode(), is(not(V3.hashCode())));

        // verify
        assertThat(V3.hashCode(), is(not(V1.hashCode())));
        assertThat(V3.hashCode(), is(not(V2.hashCode())));
        assertThat(V3.hashCode(), is(V3.hashCode()));
    }

    @Test
    public void testEquals() {
        // verify
        assertTrue(V1.equals(V1));
        assertFalse(V1.equals(V2));
        assertFalse(V1.equals(V3));

        // verify
        assertFalse(V2.equals(V1));
        assertTrue(V2.equals(V2));
        assertFalse(V2.equals(V3));

        // verify
        assertFalse(V3.equals(V1));
        assertFalse(V3.equals(V2));
        assertTrue(V3.equals(V3));
    }
}
