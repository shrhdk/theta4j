package com.theta360.ptpip.packet;

import com.theta360.test.categories.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class GUIDTest {
    @Test
    public void encodeAndDecode() {
        // given
        UUID given = UUID.randomUUID();

        // act
        byte[] bytes = GUID.toBytes(given);
        UUID actual = GUID.toUUID(bytes);

        // verify
        assertThat(actual, is(given));
    }
}
