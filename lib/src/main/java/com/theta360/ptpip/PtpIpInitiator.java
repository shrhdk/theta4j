package com.theta360.ptpip;

import com.theta360.ptp.AbstractPtp;
import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpEventListenerSet;
import com.theta360.ptp.PtpException;
import com.theta360.ptp.code.Code;
import com.theta360.ptp.data.*;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptpip.io.PacketInputStream;
import com.theta360.ptpip.io.PacketOutputStream;
import com.theta360.ptpip.packet.*;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This test requires manual execution.
 * Execute after connection with RICOH THETA is established.
 */
public class PtpIpInitiator extends AbstractPtp {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpIpInitiator.class);

    // Property

    private final GUID guid;
    private final String host;
    private final int port;

    // State

    private volatile boolean isClosed = false;
    protected TransactionID transactionID = new TransactionID();

    // EventListener

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final PtpEventListenerSet listenerSet = new PtpEventListenerSet();

    // Command Data Connection

    private Socket commandDataConnection;
    protected PacketInputStream ci;
    protected PacketOutputStream co;

    // Event Connection

    private Socket eventConnection;
    private PacketInputStream ei;

    // Connect

    public PtpIpInitiator(GUID guid, String host, int port) throws IOException {
        Validators.validateNonNull("guid", guid);
        Validators.validateNonNull("host", host);

        this.guid = guid;
        this.host = host;
        this.port = port;

        UINT32 connectionNumber = establishCommandDataConnection();
        establishEventConnection(connectionNumber);
        startEventHandlerThread();
    }

    private UINT32 establishCommandDataConnection() throws IOException {
        commandDataConnection = new Socket(host, port);
        ci = new PacketInputStream(commandDataConnection.getInputStream());
        co = new PacketOutputStream(commandDataConnection.getOutputStream());

        InitCommandRequestPacket initCommandRequest = new InitCommandRequestPacket(guid, "test", ProtocolVersions.REV_1_0);
        co.write(initCommandRequest);
        LOGGER.debug("Sent InitCommandRequest: " + initCommandRequest);

        InitCommandAckPacket initCommandAck = ci.readInitCommandAckPacket();
        LOGGER.debug("Command Data Connection is established: " + initCommandAck);

        return initCommandAck.getConnectionNumber();
    }

    private void establishEventConnection(UINT32 connectionNumber) throws IOException {
        eventConnection = new Socket(host, port);
        ei = new PacketInputStream(eventConnection.getInputStream());
        PacketOutputStream eo = new PacketOutputStream(eventConnection.getOutputStream());

        InitEventRequestPacket initEventRequest = new InitEventRequestPacket(connectionNumber);
        eo.write(initEventRequest);
        LOGGER.debug("Sent InitEventRequest: " + initEventRequest);

        InitEventAckPacket initEventAck = ei.readInitEventAckPacket();
        LOGGER.debug("Event Connection is established: " + initEventAck);
    }

    private void startEventHandlerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    final EventPacket eventPacket;
                    try {
                        eventPacket = ei.readEventPacket();
                    } catch (final IOException e) {
                        if (isClosed) {
                            LOGGER.debug("Finished Event Listener Thread.");
                            return;
                        }

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                listenerSet.onError(e);
                            }
                        });
                        continue;
                    }

                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Event event = new Event(
                                    eventPacket.getEventCode(),
                                    getSessionID(),
                                    eventPacket.getTransactionID(),
                                    eventPacket.getP1(),
                                    eventPacket.getP2(),
                                    eventPacket.getP3()
                            );

                            listenerSet.raise(event);
                        }
                    });
                }
            }
        }).start();
    }

    // Getter

    /**
     * Get the host of responder which the initiator is connecting.
     */
    public String getHost() {
        return host;
    }

    /**
     * Get the TCP port of responder which the initiator is connecting.
     */
    public int getPort() {
        return port;
    }

    // AbstractPtp

    @Override
    public UINT32 sendOperation(Code<UINT16> code, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) throws IOException {
        UINT32 transactionID = this.transactionID.next();

        OperationRequestPacket operationRequestPacket = new OperationRequestPacket(
                new UINT32(1),
                code.value(),
                transactionID,
                p1, p2, p3, p4, p5
        );
        co.write(operationRequestPacket);
        LOGGER.debug("Sent OperationRequest: " + operationRequestPacket);

        return transactionID;
    }

    @Override
    public Response receiveResponse() throws IOException {
        if (ci.nextType() != PtpIpPacket.Type.OPERATION_RESPONSE) {
            throw new RuntimeException("Expected OperationResponse but was " + ci.nextType());
        }

        OperationResponsePacket operationResponsePacket = ci.readOperationResponsePacket();

        return new Response(
                operationResponsePacket.getResponseCode(),
                getSessionID(),
                operationResponsePacket.getTransactionID(),
                operationResponsePacket.getP1(),
                operationResponsePacket.getP2(),
                operationResponsePacket.getP3(),
                operationResponsePacket.getP4(),
                operationResponsePacket.getP5()
        );
    }

    @Override
    public void sendData(byte[] data) throws IOException {
        co.writeData(transactionID.next(), data);
    }

    @Override
    public void receiveData(OutputStream dst) throws IOException, PtpException {
        ci.readData(dst);
    }

    // Listener

    public final boolean addListener(PtpEventListener listener) {
        return listenerSet.add(listener);
    }

    public final boolean removeListener(PtpEventListener listener) {
        return listenerSet.remove(listener);
    }

    // Closeable

    @Override
    public void close() throws IOException {
        isClosed = true;
        listenerSet.clear();

        // (1) Close Event Connection
        if (eventConnection != null) {
            eventConnection.close();
        }

        // (2) Close Command Data Connection
        if (commandDataConnection != null) {
            commandDataConnection.close();
        }

        // (3) Shutdown Listener Executor
        executor.shutdown();
    }
}
