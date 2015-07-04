package org.theta4j.ptp;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.data.*;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptp.type.UINT64;
import org.theta4j.ptp.type.UINT8;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface PtpInitiator extends Closeable {
    // Operation (Base)

    UINT32 sendOperation(Code<UINT16> operationCode) throws IOException;

    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1) throws IOException;

    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2) throws IOException;

    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3) throws IOException;

    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) throws IOException;

    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) throws IOException;

    // Operations

    /**
     * Get the DeviceInfo of the responder.
     *
     * @throws IOException
     */
    DeviceInfo getDeviceInfo() throws IOException;

    /**
     * Open the session.
     *
     * @param sessionID
     * @throws IOException
     */
    void openSession(UINT32 sessionID) throws IOException;

    /**
     * Close the session.
     *
     * @throws IOException
     */
    void closeSession() throws IOException;

    /**
     * Get list of storage ID
     *
     * @throws IOException
     */
    List<UINT32> getStorageIDs() throws IOException;

    /**
     * Get storage info
     *
     * @param storageID
     * @throws IOException
     */
    StorageInfo getStorageInfo(UINT32 storageID) throws IOException;

    /**
     * Get number of objects.
     *
     * @throws IOException
     */
    UINT32 getNumObjects() throws IOException;

    /**
     * Get list of object handle.
     *
     * @throws IOException
     */
    List<UINT32> getObjectHandles() throws IOException;

    /**
     * Get list of object handle.
     *
     * @param storageID
     * @throws IOException
     */
    List<UINT32> getObjectHandles(UINT32 storageID) throws IOException;

    /**
     * Get the information of the object.
     *
     * @param objectHandle
     * @throws IOException
     */
    ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException;

    /**
     * Get the object from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    void getObject(UINT32 objectHandle, OutputStream dst) throws IOException;

    /**
     * Get the thumbnail of specified object handle from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException;

    /**
     * Delete the specified object.
     *
     * @param objectHandle
     * @throws IOException
     */
    void deleteObject(UINT32 objectHandle) throws IOException;

    /**
     * Send initiate capture request to the responder.
     *
     * @throws IOException
     */
    void initiateCapture() throws IOException;

    /**
     * Get the description of the device property.
     *
     * @throws IOException
     */
    DevicePropDesc<?> getDevicePropDesc(Code<UINT16> devicePropCode) throws IOException;

    InputStream getDevicePropValue(Code<UINT16> devicePropCode) throws IOException;

    UINT8 getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException;

    UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException;

    UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException;

    UINT64 getDevicePropValueAsUINT64(Code<UINT16> devicePropCode) throws IOException;

    String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException;

    void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException;

    void setDevicePropValue(Code<UINT16> devicePropValue, byte value) throws IOException;

    void setDevicePropValue(Code<UINT16> devicePropValue, UINT16 value) throws IOException;

    void setDevicePropValue(Code<UINT16> devicePropValue, UINT32 value) throws IOException;

    void setDevicePropValue(Code<UINT16> devicePropValue, String value) throws IOException;

    /**
     * Terminate all capture.
     *
     * @throws IOException
     */
    void terminateOpenCapture() throws IOException;

    /**
     * Terminate specified capture.
     *
     * @param transactionID
     * @throws IOException
     */
    void terminateOpenCapture(UINT32 transactionID) throws IOException;

    /**
     * Initiate open capture.
     *
     * @return Transaction ID
     * @throws IOException
     * @see #terminateOpenCapture
     */
    UINT32 initiateOpenCapture() throws IOException;

    // Responses

    Response receiveResponse() throws IOException;

    void checkResponse() throws IOException;

    // Data

    void sendData(byte[] data) throws IOException;

    InputStream receiveData() throws IOException;

    void receiveData(OutputStream dst) throws IOException;

    // Listener

    /**
     * Add the listener for PTP event.
     *
     * @param listener
     * @return true if this initiator did not already contain the specified listener
     */
    boolean addListener(PtpEventListener listener);

    /**
     * Remove the listener for PTP event.
     *
     * @param listener
     * @return true if this initiator contained the specified listener
     */
    boolean removeListener(PtpEventListener listener);
}
