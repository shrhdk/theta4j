package org.theta4j.ptpip;

import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class PtpIpInitiatorTest {
    private static final UUID GUID = UUID.randomUUID();
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 15740;

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void withNullGUID() throws IOException {
        // act
        new PtpIpInitiator(null, HOST, PORT);
    }

    @Test(expected = NullPointerException.class)
    public void withNullHost() throws IOException {
        // act
        new PtpIpInitiator(GUID, null, PORT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withTooSmallPortNumber() throws IOException {
        // act
        new PtpIpInitiator(GUID, HOST, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withTooLargePortNumber() throws IOException {
        // act
        new PtpIpInitiator(GUID, HOST, 65536);
    }
}
