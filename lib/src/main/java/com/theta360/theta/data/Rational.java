package com.theta360.theta.data;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.ArrayUtils;
import com.theta360.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Exif2.3 standard RATIONAL
 */
public class Rational {
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

    public static Rational valueOf(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);
        Validators.validateLength("bytes", bytes, SIZE_IN_BYTES);

        try (
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                PtpInputStream pis = new PtpInputStream(bais)
        ) {
            UINT32 molecule = pis.readUINT32();
            UINT32 denominator = pis.readUINT32();

            return new Rational(molecule.longValue(), denominator.longValue());
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    // Getter

    public long getMolecule() {
        return molecule;
    }

    public long getDenominator() {
        return denominator;
    }

    public byte[] bytes() {
        return bytes.clone();
    }

    // Basic Method

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(molecule)
                .append(denominator)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Rational{" + molecule + "/" + denominator + "}";
    }
}
