package org.theta4j.ptp;

import org.junit.Test;
import org.theta4j.ptp.type.UINT16;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PtpExceptionTest {
    private static final UINT16 CODE = new UINT16(1);
    private static final String MESSAGE = "message";
    private static final Throwable CAUSE = new Throwable();

    // Constructor

    @Test
    public void constructWithCode() {
        // act
        PtpException actual = new PtpException(CODE);

        // verify
        assertThat(actual.value(), is(CODE));
        assertThat(actual.getMessage(), is((String) null));
        assertThat(actual.getCause(), is((Throwable) null));
    }

    @Test
    public void constructWithCodeAndMessage() {
        // act
        PtpException actual = new PtpException(CODE, MESSAGE);

        // verify
        assertThat(actual.value(), is(CODE));
        assertThat(actual.getMessage(), is(MESSAGE));
        assertThat(actual.getCause(), is((Throwable) null));
    }

    @Test
    public void constructWithCodeAndMessageAndCause() {
        // act
        PtpException actual = new PtpException(CODE, MESSAGE, CAUSE);

        // verify
        assertThat(actual.value(), is(CODE));
        assertThat(actual.getMessage(), is(MESSAGE));
        assertThat(actual.getCause(), is(CAUSE));
    }

    @Test
    public void constructWithCodeAndCause() {
        // act
        PtpException actual = new PtpException(CODE, CAUSE);

        // verify
        assertThat(actual.value(), is(CODE));
        assertThat(actual.getMessage(), is(CAUSE.toString()));
        assertThat(actual.getCause(), is(CAUSE));
    }
}
