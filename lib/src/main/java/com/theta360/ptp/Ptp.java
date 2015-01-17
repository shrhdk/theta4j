package com.theta360.ptp;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.data.*;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface Ptp extends Closeable {
    // Operation (Base)

    UINT32 sendOperation(Code<UINT16> code) throws IOException;

    UINT32 sendOperation(Code<UINT16> code, UINT32 p1) throws IOException;

    UINT32 sendOperation(Code<UINT16> code, UINT32 p1, UINT32 p2) throws IOException;

    UINT32 sendOperation(Code<UINT16> code, UINT32 p1, UINT32 p2, UINT32 p3) throws IOException;

    UINT32 sendOperation(Code<UINT16> code, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) throws IOException;

    UINT32 sendOperation(Code<UINT16> code, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) throws IOException;

    // Operations

    /**
     * Get the DeviceInfo of the responder.
     *
     * @throws IOException
     */
    DeviceInfo getDeviceInfo() throws IOException, PtpException;

    /**
     * Open the session.
     *
     * @param sessionID
     * @throws IOException
     */
    void openSession(UINT32 sessionID) throws IOException, PtpException;

    /**
     * Close the session.
     *
     * @throws IOException
     */
    void closeSession() throws IOException, PtpException;

    /**
     * Get list of storage ID
     *
     * @throws IOException
     */
    List<UINT32> getStorageIDs() throws IOException, PtpException;

    /**
     * Get storage info
     *
     * @param storageID
     * @throws IOException
     */
    StorageInfo getStorageInfo(UINT32 storageID) throws IOException, PtpException;

    /**
     * Get number of objects.
     *
     * @throws IOException
     */
    UINT32 getNumObjects() throws IOException, PtpException;

    /**
     * Get list of object handle.
     *
     * @throws IOException
     */
    List<UINT32> getObjectHandles() throws IOException, PtpException;

    /**
     * Get list of object handle.
     *
     * @param storageID
     * @throws IOException
     */
    List<UINT32> getObjectHandles(UINT32 storageID) throws IOException, PtpException;

    /**
     * Get the information of the object.
     *
     * @param objectHandle
     * @throws IOException
     */
    ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException, PtpException;

    /**
     * Get the object from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    void getObject(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException;

    /**
     * Get the thumbnail of specified object handle from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException;

    /**
     * Delete the specified object.
     *
     * @param objectHandle
     * @throws IOException
     */
    void deleteObject(UINT32 objectHandle) throws IOException, PtpException;

    /**
     * Send initiate capture request to the responder.
     *
     * @throws IOException
     */
    void initiateCapture() throws IOException, PtpException;

    /**
     * Get the description of the device property.
     *
     * @throws IOException
     */
    DevicePropDesc<?> getDevicePropDesc(Code<UINT16> devicePropCode) throws IOException, PtpException;

    byte[] getDevicePropValue(Code<UINT16> devicePropCode) throws IOException, PtpException;

    byte getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException, PtpException;

    UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException, PtpException;

    UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException, PtpException;

    String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException, PtpException;

    void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException, PtpException;

    void setDevicePropValue(Code<UINT16> devicePropValue, byte value) throws IOException, PtpException;

    void setDevicePropValue(Code<UINT16> devicePropValue, UINT16 value) throws IOException, PtpException;

    void setDevicePropValue(Code<UINT16> devicePropValue, UINT32 value) throws IOException, PtpException;

    void setDevicePropValue(Code<UINT16> devicePropValue, String value) throws IOException, PtpException;

    /**
     * Terminate all capture.
     *
     * @throws IOException
     */
    void terminateOpenCapture() throws IOException, PtpException;

    /**
     * Terminate specified capture.
     *
     * @param transactionID
     * @throws IOException
     */
    void terminateOpenCapture(UINT32 transactionID) throws IOException, PtpException;

    /**
     * Initiate open capture.
     *
     * @return Transaction ID
     * @throws IOException
     * @see #terminateOpenCapture
     */
    UINT32 initiateOpenCapture() throws IOException, PtpException;

    // Responses

    Response receiveResponse() throws IOException;

    void checkResponse() throws IOException, PtpException;

    // Data

    void sendData(byte[] data) throws IOException;

    byte[] receiveData() throws IOException, PtpException;

    void receiveData(OutputStream dst) throws IOException, PtpException;

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
