package com.theta360.ptp.io;

import com.theta360.ptp.data.TransactionID;
import com.theta360.ptp.packet.EndDataPacket;
import com.theta360.ptp.packet.PtpIpPacket;
import com.theta360.ptp.packet.StartDataPacket;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.test.categories.UnitTest;
import com.theta360.util.ByteUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Category(UnitTest.class)
public class PacketOutputStreamTest {
    private ByteArrayOutputStream baos;
    private PacketOutputStream pos;

    @Before
    public void setUp() {
        baos = new ByteArrayOutputStream();
        pos = new PacketOutputStream(baos);
    }

    @After
    public void cleanUp() throws IOException {
        pos.close();
    }

    @Test
    public void write() throws IOException {
        // given
        byte[] given = new byte[]{0x12, 0x34};

        // expected
        byte[] expected = new byte[]{
                0x0A, 0x00, 0x00, 0x00, // Length
                0x01, 0x00, 0x00, 0x00, // Type
                0x12, 0x34
        };

        // act
        pos.write(new PtpIpPacket(PtpIpPacket.Type.INIT_COMMAND_REQUEST, given));

        // verify
        byte[] actual = baos.toByteArray();
        assertThat(actual, is(expected));
    }

    @Test
    public void writeData() throws IOException {
        // given
        byte[] given = new byte[]{0x12, 0x34};

        // expected
        byte[] expected = ByteUtils.join(
                new StartDataPacket(new UINT32(1), new UINT64(given.length)).bytes(),
                new EndDataPacket(new UINT32(2), given).bytes()
        );

        // act
        pos.writeData(new TransactionID(), given);

        // verify
        byte[] actual = baos.toByteArray();
        assertThat(actual, is(expected));
    }
}
