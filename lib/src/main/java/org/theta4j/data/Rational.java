/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;
import org.theta4j.util.Closer;
import org.theta4j.util.Validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Exif 2.3 standard RATIONAL
 */
public class Rational implements Serializable {
    private final long molecule;
    private final long denominator;
    private final byte[] bytes;

    // Utility Field

    public static final int SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES;

    // Constructor

    public Rational(long molecule, long denominator) {
        if (molecule < 0) {
            throw new IllegalArgumentException();
        }

        if (denominator < 0) {
            throw new IllegalArgumentException();
        }

        this.molecule = molecule;
        this.denominator = denominator;

        this.bytes = ArrayUtils.join(
                new UINT32(molecule).bytes(),
                new UINT32(denominator).bytes()
        );
    }

    // Static Factory Method

    /**
     * Returns the
     */
    public static Rational valueOf(byte[] bytes) {
        Validators.notNull("bytes", bytes);
        Validators.length("bytes", bytes, SIZE_IN_BYTES);

        final Closer closer = new Closer();
        try {
            final ByteArrayInputStream bais = closer.push(new ByteArrayInputStream(bytes));
            final PtpInputStream pis = closer.push(new PtpInputStream(bais));

            UINT32 molecule = pis.readUINT32();
            UINT32 denominator = pis.readUINT32();

            return new Rational(molecule.longValue(), denominator.longValue());
        } catch (IOException e) {
            throw new AssertionError(e);
        } finally {
            closer.close();
        }
    }

    // Getter

    /**
     * Returns the molecule of this rational value.
     */
    public long getMolecule() {
        return molecule;
    }

    /**
     * Returns the denominator of this rational value.
     */
    public long getDenominator() {
        return denominator;
    }

    /**
     * Returns the byte array according to the Exif 2.3 standard RATIONAL.
     */
    public byte[] bytes() {
        return bytes.clone();
    }

    // Basic Method

    /**
     * Compares strictly, 1/2 equals to 1/2, but 1/2 does not equal to 2/4.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rational rhs = (Rational) o;

        return new EqualsBuilder()
                .append(molecule, rhs.molecule)
                .append(denominator, rhs.denominator)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(molecule)
                .append(denominator)
                .toHashCode();
    }

    /**
     * Returns the string such as 1/1, 2/2, and 3/5.
     */
    @Override
    public String toString() {
        return "Rational{" + molecule + "/" + denominator + "}";
    }
}
