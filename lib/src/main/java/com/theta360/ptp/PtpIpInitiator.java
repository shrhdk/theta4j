package com.theta360.ptp;

import com.theta360.ptp.data.*;
import com.theta360.ptp.packet.*;
import com.theta360.ptp.type.ConvertException;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class PtpIpInitiator implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpIpInitiator.class);

    private volatile boolean isClosed = false;

    private final GUID guid;
    private final String host;
    private final int port;

    // EventListener Executor
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // EventListener
    private final PtpEventListenerSet listenerSet = new PtpEventListenerSet();

    // Transaction ID
    private TransactionID transactionID = new TransactionID();

    // Command Data Connection
    private Socket commandDataConnection;
    private PtpIpInputStream ci;
    private PtpIpOutputStream co;

    // Event Connection
    private Socket eventConnection;
    private PtpIpInputStream ei;

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

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    private UINT32 establishCommandDataConnection() throws IOException {
        commandDataConnection = new Socket(host, port);
        ci = new PtpIpInputStream(commandDataConnection.getInputStream());
        co = new PtpIpOutputStream(commandDataConnection.getOutputStream());

        InitCommandRequestPacket initCommandRequest = new InitCommandRequestPacket(guid, "test", ProtocolVersions.REV_1_0);
        co.write(initCommandRequest);
        LOGGER.info("Sent InitCommandRequest: " + initCommandRequest);

        PtpIpPacket packet = ci.read();
        InitCommandAckPacket initCommandAck;
        try {
            initCommandAck = InitCommandAckPacket.valueOf(packet);
        } catch (PacketException e) {
            throw new RuntimeException("Unexpected response from responder: " + packet, e);
        }
        LOGGER.info("Command Data Connection is established: " + initCommandAck);

        return initCommandAck.getConnectionNumber();
    }

    private void establishEventConnection(UINT32 connectionNumber) throws IOException {
        eventConnection = new Socket(host, port);
        ei = new PtpIpInputStream(eventConnection.getInputStream());
        PtpIpOutputStream eo = new PtpIpOutputStream(eventConnection.getOutputStream());

        InitEventRequestPacket initEventRequest = new InitEventRequestPacket(connectionNumber);
        eo.write(initEventRequest);
        LOGGER.info("Sent InitEventRequest: " + initEventRequest);

        PtpIpPacket packet = ei.read();
        InitEventAckPacket initEventAck;
        try {
            initEventAck = InitEventAckPacket.valueOf(packet);
        } catch (PacketException e) {
            throw new RuntimeException("Unexpected response from responder: " + packet, e);
        }
        LOGGER.info("Event Connection is established: " + initEventAck);
    }

    private void startEventHandlerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    PtpIpPacket packet;
                    try {
                        packet = ei.read();
                    } catch (final IOException e) {
                        if (isClosed) {
                            LOGGER.info("Finished Event Listener Thread.");
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

                    final EventPacket eventPacket;
                    try {
                        eventPacket = EventPacket.valueOf(packet);
                    } catch (final PacketException e) {
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
                            listenerSet.onPacket(eventPacket);
                        }
                    });
                }
            }
        }).start();
    }

    public DeviceInfo getDeviceInfo() throws IOException {
        // Send OperationRequest (GetDeviceInfo)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_DEVICE_INFO.getCode(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetDeviceInfo): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        DeviceInfo deviceInfo;
        try {
            deviceInfo = DeviceInfo.valueOf(data);
        } catch (ConvertException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Received DeviceInfo: " + deviceInfo);

        // Receive OperationResponse
        PtpIpPacket operationResponsePacket = ci.read();
        OperationResponsePacket operationResponse;
        try {
            operationResponse = OperationResponsePacket.valueOf(operationResponsePacket);
        } catch (PacketException e) {
            throw new RuntimeException("Unexpected response from responder: " + operationResponsePacket, e);
        }
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return deviceInfo;
    }

    public void openSession(UINT32 sessionID) throws IOException {
        if (sessionID.longValue() == 0) {
            throw new IllegalArgumentException("sessionID must be non-zero.");
        }

        // Send OperationRequest (OpenSession)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.OPEN_SESSION.getCode(),
                transactionID.next(),
                sessionID
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (OpenSession): " + operationRequest);

        // Receive OperationResponse
        PtpIpPacket operationResponsePacket = ci.read();
        OperationResponsePacket operationResponse;
        try {
            operationResponse = OperationResponsePacket.valueOf(operationResponsePacket);
        } catch (PacketException e) {
            throw new RuntimeException("Unexpected response from responder: " + operationResponsePacket, e);
        }
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public void initiateCapture() throws IOException {
        // Send OperationRequest (InitiateCapture)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.INITIATE_CAPTURE.getCode(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (InitiateCapture): " + operationRequest);

        // Receive OperationResponse
        PtpIpPacket operationResponsePacket = ci.read();
        OperationResponsePacket operationResponse;
        try {
            operationResponse = OperationResponsePacket.valueOf(operationResponsePacket);
        } catch (PacketException e) {
            throw new RuntimeException("Unexpected response from responder: " + operationResponsePacket, e);
        }
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public void closeSession() throws IOException {
        // Send OperationRequest (CloseSession)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.CLOSE_SESSION.getCode(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (CloseSession): " + operationRequest);

        // Receive OperationResponse
        PtpIpPacket operationResponsePacket = ci.read();
        OperationResponsePacket operationResponse;
        try {
            operationResponse = OperationResponsePacket.valueOf(operationResponsePacket);
        } catch (PacketException e) {
            throw new RuntimeException("Unexpected response from responder: " + operationResponsePacket, e);
        }
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public boolean addListener(PtpEventListener listener) {
        return listenerSet.add(listener);
    }

    public boolean removeListener(PtpEventListener listener) {
        return listenerSet.remove(listener);
    }

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
