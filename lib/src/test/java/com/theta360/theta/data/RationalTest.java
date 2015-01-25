package com.theta360.theta.data;

import com.theta360.ptp.type.UINT32;
import com.theta360.util.ByteUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RationalTest {
    @Test
    public void withZeroMolecule() {
        // act
        new Rational(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withNegativeMolecule() {
        // act
        new Rational(-1, 1);
    }

    @Test(expected = ArithmeticException.class)
    public void withZeroDenominator() {
        // act
        new Rational(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withNegativeDenominator() {
        // act
        new Rational(1, -1);
    }

    @Test
    public void constructAndGet() {
        // act
        Rational actual = new Rational(1, 2);

        // verify
        assertThat(actual.getMolecule(), is(1L));
        assertThat(actual.getDenominator(), is(2L));
    }

    @Test
    public void valueOfAndGet() {
        // given
        byte[] given = ByteUtils.join(  // == 1/2
                new UINT32(1).bytes(),
                new UINT32(2).bytes()
        );

        // act
        Rational actual = Rational.valueOf(given);

        // verify
        assertThat(actual.getMolecule(), is(1L));
        assertThat(actual.getDenominator(), is(2L));
    }
}
