package org.theta4j.ptpip.io;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.theta4j.ptp.PtpException;
import org.theta4j.ptp.code.ResponseCode;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.ptpip.packet.*;
import org.theta4j.util.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class PtpIpInputStreamTest {
    public static class CheckNext {
        @Test
        public void normal() throws IOException {
            // given
            PtpIpPacket given = new InitCommandRequestPacket(UUID.randomUUID(), "test", UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act & verify
            assertThat(pis.nextType(), is(PtpIpPacket.Type.INIT_COMMAND_REQUEST));
            assertThat(pis.nextType(), is(PtpIpPacket.Type.INIT_COMMAND_REQUEST));
            assertThat(pis.readInitCommandRequestPacket(), is(given));
        }
    }

    public static class ReadPacket {
        @Test
        public void initCommandRequestPacket() throws IOException {
            // given
            PtpIpPacket given = new InitCommandRequestPacket(UUID.randomUUID(), "test", UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            InitCommandRequestPacket actual = pis.readInitCommandRequestPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void initCommandAckPacket() throws IOException {
            // given
            PtpIpPacket given = new InitCommandAckPacket(UINT32.MAX_VALUE, UUID.randomUUID(), "test", UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            InitCommandAckPacket actual = pis.readInitCommandAckPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void initEventRequestPacket() throws IOException {
            // given
            PtpIpPacket given = new InitEventRequestPacket(UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            InitEventRequestPacket actual = pis.readInitEventRequestPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void initEventAckPacket() throws IOException {
            // given
            PtpIpPacket given = new InitEventAckPacket();

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            InitEventAckPacket actual = pis.readInitEventAckPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void initFailPacket() throws IOException {
            // given
            PtpIpPacket given = new InitFailPacket(UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            InitFailPacket actual = pis.readInitFailPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void operationRequestPacket() throws IOException {
            // given
            PtpIpPacket given = new OperationRequestPacket(UINT32.MAX_VALUE, UINT16.MAX_VALUE, UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            OperationRequestPacket actual = pis.readOperationRequestPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void operationResponsePacket() throws IOException {
            // given
            PtpIpPacket given = new OperationResponsePacket(UINT16.MAX_VALUE, UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            OperationResponsePacket actual = pis.readOperationResponsePacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void eventPacket() throws IOException {
            // given
            PtpIpPacket given = new EventPacket(UINT16.MAX_VALUE, UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            EventPacket actual = pis.readEventPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void startDataPacket() throws IOException {
            // given
            PtpIpPacket given = new StartDataPacket(UINT32.MAX_VALUE, UINT64.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            StartDataPacket actual = pis.readStartDataPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void dataPacket() throws IOException {
            // given
            PtpIpPacket given = new DataPacket(UINT32.MAX_VALUE, new byte[]{0x00, 0x01, 0x02, 0x03});

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            DataPacket actual = pis.readDataPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void endDataPacket() throws IOException {
            // given
            PtpIpPacket given = new EndDataPacket(UINT32.MAX_VALUE, new byte[]{0x00, 0x01, 0x02, 0x03});

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            EndDataPacket actual = pis.readEndDataPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void cancelPacket() throws IOException {
            // given
            PtpIpPacket given = new CancelPacket(UINT32.MAX_VALUE);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            CancelPacket actual = pis.readCancelPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void probeRequestPacket() throws IOException {
            // given
            PtpIpPacket given = new ProbeRequestPacket();

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            ProbeRequestPacket actual = pis.readProbeRequestPacket();

            // verify
            assertThat(actual, is(given));
        }

        @Test
        public void probeResponsePacket() throws IOException {
            // given
            PtpIpPacket given = new ProbeResponsePacket();

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            ProbeResponsePacket actual = pis.readProbeResponsePacket();

            // verify
            assertThat(actual, is(given));
        }
    }

    public static class ReadData {
        @Test(expected = IOException.class)
        public void butWasOperationResponse() throws IOException {
            // given
            UINT32 transactionID = new UINT32(1);
            UINT16 responseCode = ResponseCode.OK.value();
            PtpIpPacket given = new OperationResponsePacket(responseCode, transactionID);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            pis.readData();
        }

        @Test(expected = PtpException.class)
        public void withError() throws IOException {
            // given
            UINT32 transactionID = new UINT32(1);
            UINT16 responseCode = ResponseCode.GENERAL_ERROR.value();
            PtpIpPacket given = new OperationResponsePacket(responseCode, transactionID);

            // arrange
            InputStream givenInputStream = new ByteArrayInputStream(given.bytes());
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            pis.readData();
        }

        @Test
        public void normal() throws IOException {
            // given
            UINT32 transactionID = new UINT32(1);
            byte[] data1 = new byte[]{0x00, 0x01, 0x02, 0x03};
            byte[] data2 = new byte[]{0x04, 0x05, 0x06, 0x07};
            byte[] data = ArrayUtils.join(data1, data2);

            // arrange
            byte[] givenBytes = ArrayUtils.join(
                    new StartDataPacket(transactionID, new UINT64(data1.length + data2.length)).bytes(),
                    new DataPacket(transactionID, data1).bytes(),
                    new EndDataPacket(transactionID, data2).bytes()
            );
            InputStream givenInputStream = new ByteArrayInputStream(givenBytes);
            PtpIpInputStream pis = new PtpIpInputStream(givenInputStream);

            // act
            byte[] actual = pis.readData();

            // verify
            assertThat(actual, is(data));
        }
    }
}
