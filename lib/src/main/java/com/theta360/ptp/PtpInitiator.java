package com.theta360.ptp;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.code.OperationCode;
import com.theta360.ptp.code.ResponseCode;
import com.theta360.ptp.data.*;
import com.theta360.ptp.io.PacketInputStream;
import com.theta360.ptp.io.PacketOutputStream;
import com.theta360.ptp.packet.*;
import com.theta360.ptp.type.AUINT32;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This test requires manual execution.
 * Execute after connection with RICOH THETA is established.
 */
public class PtpInitiator implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpInitiator.class);

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

    protected PtpInitiator(GUID guid, String host, int port) throws IOException {
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
                            listenerSet.onPacket(eventPacket);
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

    // Helper

    protected final UINT32 sendOperationRequest(Code<UINT16> code) throws IOException {
        return sendOperationRequest(code, UINT32.ZERO);
    }

    protected final UINT32 sendOperationRequest(Code<UINT16> code, UINT32 p1) throws IOException {
        return sendOperationRequest(code, p1, UINT32.ZERO);
    }

    protected final UINT32 sendOperationRequest(Code<UINT16> code, UINT32 p1, UINT32 p2) throws IOException {
        return sendOperationRequest(code, p1, p2, UINT32.ZERO);
    }

    protected final UINT32 sendOperationRequest(Code<UINT16> code, UINT32 p1, UINT32 p2, UINT32 p3) throws IOException {
        UINT32 transactionID = this.transactionID.next();

        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                code.value(),
                transactionID,
                p1, p2, p3
        );
        co.write(operationRequest);
        LOGGER.debug("Sent OperationRequest: " + operationRequest);

        return transactionID;
    }

    protected final void receiveOperationResponse() throws IOException, PtpException {
        if (ci.nextType() != PtpIpPacket.Type.OPERATION_RESPONSE) {
            throw new RuntimeException("Expected OperationResponse but was " + ci.nextType());
        }

        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();

        if (!operationResponse.getResponseCode().equals(ResponseCode.OK.value())) {
            String message = "Response Code is not OK: " + operationResponse.getResponseCode();
            throw new PtpException(operationResponse.getResponseCode().intValue(), message);
        }
        LOGGER.debug("Received OperationResponse: " + operationResponse);
    }

    // Operations

    /**
     * Get the DeviceInfo of the responder.
     *
     * @throws IOException
     */
    public DeviceInfo getDeviceInfo() throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_DEVICE_INFO);
        DeviceInfo deviceInfo = DeviceInfo.valueOf(ci.readData());
        receiveOperationResponse();

        return deviceInfo;
    }

    /**
     * Open the session.
     *
     * @param sessionID
     * @throws IOException
     */
    public void openSession(UINT32 sessionID) throws IOException, PtpException {
        Validators.validateNonNull("sessionID", sessionID);

        if (sessionID.longValue() == 0) {
            throw new IllegalArgumentException("sessionID must be non-zero.");
        }

        sendOperationRequest(OperationCode.OPEN_SESSION, sessionID);
        receiveOperationResponse();
    }

    /**
     * Close the session.
     *
     * @throws IOException
     */
    public void closeSession() throws IOException, PtpException {
        sendOperationRequest(OperationCode.CLOSE_SESSION);
        receiveOperationResponse();
    }

    /**
     * Get list of storage ID
     *
     * @throws IOException
     */
    public List<UINT32> getStorageIDs() throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_STORAGE_IDS);
        List<UINT32> storageIDs = AUINT32.valueOf(ci.readData());
        receiveOperationResponse();

        return storageIDs;
    }

    /**
     * Get storage info
     *
     * @param storageID
     * @throws IOException
     */
    public StorageInfo getStorageInfo(UINT32 storageID) throws IOException, PtpException {
        Validators.validateNonNull("storageID", storageID);

        sendOperationRequest(OperationCode.GET_STORAGE_INFO, storageID);
        StorageInfo storageInfo = StorageInfo.valueOf(ci.readData());
        receiveOperationResponse();

        return storageInfo;
    }

    /**
     * Get number of objects.
     *
     * @throws IOException
     */
    public UINT32 getNumObjects() throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_NUM_OBJECTS);
        UINT32 numObjects = UINT32.valueOf(ci.readData());
        receiveOperationResponse();

        return numObjects;
    }

    /**
     * Get list of object handle.
     *
     * @throws IOException
     */
    public List<UINT32> getObjectHandles() throws IOException, PtpException {
        return getObjectHandles(new UINT32(0xFFFFFFFFL));
    }

    /**
     * Get list of object handle.
     *
     * @param storageID
     * @throws IOException
     */
    public List<UINT32> getObjectHandles(UINT32 storageID) throws IOException, PtpException {
        Validators.validateNonNull("storageID", storageID);

        sendOperationRequest(OperationCode.GET_OBJECT_HANDLES, storageID);
        List<UINT32> objectHandles = AUINT32.valueOf(ci.readData());
        receiveOperationResponse();

        return objectHandles;
    }

    /**
     * Get the information of the object.
     *
     * @param objectHandle
     * @throws IOException
     */
    public ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);

        sendOperationRequest(OperationCode.GET_OBJECT_INFO, objectHandle);
        ObjectInfo objectInfo = ObjectInfo.valueOf(ci.readData());
        receiveOperationResponse();

        return objectInfo;
    }

    /**
     * Get the object from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    public void getObject(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        sendOperationRequest(OperationCode.GET_OBJECT, objectHandle);
        ci.readData(dst);
        receiveOperationResponse();
    }

    /**
     * Get the thumbnail of specified object handle from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    public void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        sendOperationRequest(OperationCode.GET_THUMB, objectHandle);
        ci.readData(dst);
        receiveOperationResponse();
    }

    /**
     * Delete the specified object.
     *
     * @param objectHandle
     * @throws IOException
     */
    public void deleteObject(UINT32 objectHandle) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);

        sendOperationRequest(OperationCode.DELETE_OBJECT, objectHandle);
        receiveOperationResponse();
    }

    /**
     * Send initiate capture request to the responder.
     *
     * @throws IOException
     */
    public void initiateCapture() throws IOException, PtpException {
        sendOperationRequest(OperationCode.INITIATE_CAPTURE);
        receiveOperationResponse();
    }

    /**
     * Terminate all capture.
     *
     * @throws IOException
     */
    public void terminateOpenCapture() throws IOException, PtpException {
        terminateOpenCapture(UINT32.MAX_VALUE);
    }

    /**
     * Terminate specified capture.
     *
     * @param transactionID
     * @throws IOException
     */
    public void terminateOpenCapture(UINT32 transactionID) throws IOException, PtpException {
        sendOperationRequest(OperationCode.TERMINATE_OPEN_CAPTURE, transactionID);
        receiveOperationResponse();
    }

    /**
     * Initiate open capture.
     *
     * @return Transaction ID
     * @throws IOException
     * @see #terminateOpenCapture
     */
    public UINT32 initiateOpenCapture() throws IOException, PtpException {
        UINT32 transactionID = sendOperationRequest(OperationCode.INITIATE_OPEN_CAPTURER);
        receiveOperationResponse();

        return transactionID;
    }

    // Property

    /**
     * Get the description of the device property.
     *
     * @throws IOException
     */
    public DevicePropDesc<?> getDevicePropDesc(Code<UINT16> devicePropCode) throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_DEVICE_PROP_DESC, new UINT32(devicePropCode.value().intValue()));
        DevicePropDesc<?> devicePropDesc = DevicePropDesc.valueOf(ci.readData());
        receiveOperationResponse();

        return devicePropDesc;
    }

    // Property Getter

    protected final byte[] getDevicePropValue(Code<UINT16> devicePropCode) throws IOException, PtpException {
        Validators.validateNonNull("devicePropCode", devicePropCode);

        sendOperationRequest(OperationCode.GET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        byte[] value = ci.readData();
        receiveOperationResponse();

        return value;
    }

    protected final byte getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return getDevicePropValue(devicePropCode)[0];
    }

    protected final UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return UINT16.valueOf(getDevicePropValue(devicePropCode));
    }

    protected final UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return UINT32.valueOf(getDevicePropValue(devicePropCode));
    }

    protected final String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return STR.valueOf(getDevicePropValue(devicePropCode));
    }

    // Property Setter

    protected final void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException, PtpException {
        Validators.validateNonNull("devicePropCode", devicePropCode);
        Validators.validateNonNull("value", value);

        sendOperationRequest(OperationCode.SET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        co.writeData(transactionID, value);
        receiveOperationResponse();
    }

    protected final void setDevicePropValue(Code<UINT16> devicePropValue, byte value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, new byte[]{value});
    }

    protected final void setDevicePropValue(Code<UINT16> devicePropValue, UINT16 value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    protected final void setDevicePropValue(Code<UINT16> devicePropValue, UINT32 value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    protected final void setDevicePropValue(Code<UINT16> devicePropValue, String value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, STR.toBytes(value));
    }

    // Listener

    /**
     * Add the listener for PTP event.
     *
     * @param listener
     * @return true if this initiator did not already contain the specified listener
     */
    public final boolean addListener(PtpEventListener listener) {
        return listenerSet.add(listener);
    }

    /**
     * Remove the listener for PTP event.
     *
     * @param listener
     * @return true if this initiator contained the specified listener
     */
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
