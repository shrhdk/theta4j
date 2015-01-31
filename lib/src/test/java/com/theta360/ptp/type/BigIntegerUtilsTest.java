package com.theta360.ptp.type;

import org.junit.Test;

import java.math.BigInteger;

import static com.theta360.ptp.type.BigIntegerUtils.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BigIntegerUtilsTest {
    @Test
    public void testMinOfSigned() {
        assertThat(minOfSigned(1), is(BigInteger.valueOf(Byte.MIN_VALUE)));
        assertThat(minOfSigned(2), is(BigInteger.valueOf(Short.MIN_VALUE)));
        assertThat(minOfSigned(4), is(BigInteger.valueOf(Integer.MIN_VALUE)));
        assertThat(minOfSigned(8), is(BigInteger.valueOf(Long.MIN_VALUE)));
        assertThat(minOfSigned(16), is(new BigInteger("-80000000000000000000000000000000", 16)));
    }

    @Test
    public void testMaxOfSigned() {
        assertThat(maxOfSigned(1), is(BigInteger.valueOf(Byte.MAX_VALUE)));
        assertThat(maxOfSigned(2), is(BigInteger.valueOf(Short.MAX_VALUE)));
        assertThat(maxOfSigned(4), is(BigInteger.valueOf(Integer.MAX_VALUE)));
        assertThat(maxOfSigned(8), is(BigInteger.valueOf(Long.MAX_VALUE)));
        assertThat(maxOfSigned(16), is(new BigInteger("+7fffffffffffffffffffffffffffffff", 16)));
    }

    @Test
    public void testMinOfUnsigned() {
        assertThat(minOfUnsigned(1), is(BigInteger.ZERO));
        assertThat(minOfUnsigned(2), is(BigInteger.ZERO));
        assertThat(minOfUnsigned(4), is(BigInteger.ZERO));
        assertThat(minOfUnsigned(8), is(BigInteger.ZERO));
        assertThat(minOfUnsigned(16), is(BigInteger.ZERO));
    }

    @Test
    public void testMaxOfUnsigned() {
        assertThat(maxOfUnsigned(1), is(BigInteger.valueOf(0xff)));
        assertThat(maxOfUnsigned(2), is(BigInteger.valueOf(0xffff)));
        assertThat(maxOfUnsigned(4), is(BigInteger.valueOf(0xffffffffL)));
        assertThat(maxOfUnsigned(8), is(new BigInteger("+ffffffffffffffff", 16)));
        assertThat(maxOfUnsigned(16), is(new BigInteger("+ffffffffffffffffffffffffffffffff", 16)));
    }
}
