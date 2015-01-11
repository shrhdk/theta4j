package com.theta360.ptp.type;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.packet.InitCommandRequestPacket;
import com.theta360.ptp.packet.PtpIpPacket;
import com.theta360.test.categories.UnitTest;
import com.theta360.util.ByteUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.theta360.ptp.packet.PtpIpPacket.Type.INIT_COMMAND_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(UnitTest.class)
public class PtpIpStringTest {
    private static Charset CHARSET = Charset.forName("UTF-16LE");

    // toBytes

    @Test(expected = NullPointerException.class)
    public void toBytesWithNull() {
        // act
        PtpIpString.toBytes(null);
    }

    @Test
    public void toBytesWithEmpty() {
        // given
        String given = "";

        // expected
        byte[] expected = (given + "\u0000").getBytes(CHARSET);

        // act
        byte[] actual = PtpIpString.toBytes("");

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void toBytes() {
        // given
        String given = "test";

        // expected
        byte[] expected = (given + "\u0000").getBytes(CHARSET);

        // act
        byte[] actual = PtpIpString.toBytes(given);

        // verify
        assertThat(actual, is(expected));
    }

    // read with error

    @Test(expected = NullPointerException.class)
    public void readWithNull() throws IOException {
        PtpIpString.read(null);
    }


    @Test(expected = RuntimeException.class)
    public void readInvalidName() throws IOException {
        // given
        byte[] invalidNameBytes = new byte[]{0x01};  // Not end with 0x00

        // arrange
        PtpInputStream pis = new PtpInputStream(new ByteArrayInputStream(invalidNameBytes));

        // act
        pis.readPtpIpString();
    }

    // read

    @Test
    public void readWithEmpty() throws IOException {
        // given
        byte[] given = "\u0000".getBytes(CHARSET);

        // expected
        String expected = "";

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        String actual = PtpIpString.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    @Test
    public void read() throws IOException {
        // given
        byte[] given = "test\u0000".getBytes(CHARSET);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // expected
        String expected = "test";

        // act
        String actual = PtpIpString.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }
}
