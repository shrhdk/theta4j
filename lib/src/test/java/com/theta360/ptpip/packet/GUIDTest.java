package com.theta360.ptpip.packet;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
