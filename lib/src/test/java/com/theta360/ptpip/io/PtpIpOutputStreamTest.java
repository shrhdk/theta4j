package com.theta360.ptpip.io;

import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.ptpip.packet.CancelPacket;
import com.theta360.ptpip.packet.EndDataPacket;
import com.theta360.ptpip.packet.PtpIpPacket;
import com.theta360.ptpip.packet.StartDataPacket;
import com.theta360.util.ByteUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        byte[] expected = ByteUtils.join(
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
