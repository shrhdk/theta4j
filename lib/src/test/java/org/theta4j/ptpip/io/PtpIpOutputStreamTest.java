package org.theta4j.ptpip.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.ptpip.packet.CancelPacket;
import org.theta4j.ptpip.packet.EndDataPacket;
import org.theta4j.ptpip.packet.PtpIpPacket;
import org.theta4j.ptpip.packet.StartDataPacket;
import org.theta4j.util.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PtpIpOutputStreamTest {
    private ByteArrayOutputStream baos;
    private PtpIpOutputStream pos;

    @Before
    public void setUp() {
        baos = new ByteArrayOutputStream();
        pos = new PtpIpOutputStream(baos);
    }

    @After
    public void cleanUp() throws IOException {
        pos.close();
    }

    @Test
    public void write() throws IOException {
        // given
        PtpIpPacket given = new CancelPacket(UINT32.MAX_VALUE);

        // expected
        byte[] expected = given.bytes();

        // act
        pos.write(new CancelPacket(UINT32.MAX_VALUE));

        // verify
        byte[] actual = baos.toByteArray();
        assertThat(actual, is(expected));
    }

    @Test
    public void writeData() throws IOException {
        // given
        UINT32 transactionID = new UINT32(2);
        byte[] given = new byte[]{0x12, 0x34};

        // expected
        byte[] expected = ArrayUtils.join(
                new StartDataPacket(transactionID, new UINT64(given.length)).bytes(),
                new EndDataPacket(transactionID, given).bytes()
        );

        // act
        pos.writeData(transactionID, given);

        // verify
        byte[] actual = baos.toByteArray();
        assertThat(actual, is(expected));
    }
}
