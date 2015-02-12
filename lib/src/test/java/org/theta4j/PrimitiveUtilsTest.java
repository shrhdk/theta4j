package org.theta4j;

import org.theta4j.ptp.type.UINT32;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PrimitiveUtilsTest {
    // Constructor

    @Test
    public void isUtilClass() throws Throwable {
        assertTrue(TestUtils.isUtilClass(PrimitiveUtils.class));
    }

    // convert

    @Test(expected = NullPointerException.class)
    public void convertNullUINT32List() {
        PrimitiveUtils.convert(null);
    }

    @Test
    public void convertEmptyUINT32List() {
        // given
        List<UINT32> given = new ArrayList<>();

        // expected
        List<Long> expected = new ArrayList<>();

        // act
        List<Long> actual = PrimitiveUtils.convert(given);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void convertUINT32List() {
        // given
        List<UINT32> given = new ArrayList<>();
        given.add(new UINT32(0));
        given.add(new UINT32(1));
        given.add(new UINT32(2));

        // expected
        List<Long> expected = new ArrayList<>();
        expected.add(0L);
        expected.add(1L);
        expected.add(2L);

        // act
        List<Long> actual = PrimitiveUtils.convert(given);

        // verify
        assertThat(actual, is(expected));
    }
}
