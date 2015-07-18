/*
 * Copyright (C) 2015 theta4j project
 */

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

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @return Transaction ID
     * @throws IOException
     */
    UINT32 sendOperation(Code<UINT16> operationCode) throws IOException;

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @param p1            Parameter 1
     * @return Transaction ID
     * @throws IOException
     */
    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1) throws IOException;

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @param p1            Parameter 1
     * @param p2            Parameter 2
     * @return Transaction ID
     * @throws IOException
     */
    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2) throws IOException;

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @param p1            Parameter 1
     * @param p2            Parameter 2
     * @param p3            Parameter 3
     * @return Transaction ID
     * @throws IOException
     */
    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3) throws IOException;

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @param p1            Parameter 1
     * @param p2            Parameter 2
     * @param p3            Parameter 3
     * @param p4            Parameter 4
     * @return Transaction ID
     * @throws IOException
     */
    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) throws IOException;

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @param p1            Parameter 1
     * @param p2            Parameter 2
     * @param p3            Parameter 3
     * @param p4            Parameter 4
     * @param p5            Parameter 5
     * @return Transaction ID
     * @throws IOException
     */
    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) throws IOException;

    // Operations

    /**
     * Get the DeviceInfo of the PTP-Responder.
     *
     * @return Device information of the PTP-Responder
     * @throws IOException
     */
    DeviceInfo getDeviceInfo() throws IOException;

    /**
     * Open the session.
     *
     * @param sessionID Session ID
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
     * Get list of storage ID.
     *
     * @return List of storage ID which the PTP-Responder has.
     * @throws IOException
     */
    List<UINT32> getStorageIDs() throws IOException;

    /**
     * @param storageID Get storage information.
     * @return Storage information of specified by ID.
     * @throws IOException
     */
    StorageInfo getStorageInfo(UINT32 storageID) throws IOException;

    /**
     * Get number of objects.
     *
     * @return Number of objects which the PTP-Responder has.
     * @throws IOException
     */
    UINT32 getNumObjects() throws IOException;

    /**
     * Get list of object handle.
     *
     * @return List of object handles which the PTP-Responder has.
     * @throws IOException
     */
    List<UINT32> getObjectHandles() throws IOException;

    /**
     * Get list of object handle.
     *
     * @param storageID Storage ID
     * @return List of object handles which storage has.
     * @throws IOException
     */
    List<UINT32> getObjectHandles(UINT32 storageID) throws IOException;

    /**
     * Get the information of the object.
     *
     * @param objectHandle The handle of the object to get information.
     * @return Information of the object specified by handle.
     * @throws IOException
     */
    ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException;

    /**
     * Get the object from the PTP-Responder.
     *
     * @param objectHandle The handle of the object to download.
     * @param dst          The destination which to write the object received from PTP-Responder.
     * @throws IOException
     */
    void getObject(UINT32 objectHandle, OutputStream dst) throws IOException;

    /**
     * Get the thumbnail of specified object handle from the PTP-Responder.
     *
     * @param objectHandle The handle of the object to download thumbnail.
     * @param dst          The destination which to write the object thumbnail received from PTP-Responder.
     * @throws IOException
     */
    void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException;

    /**
     * Delete the specified object.
     *
     * @param objectHandle The handle of the object to delete.
     * @throws IOException
     */
    void deleteObject(UINT32 objectHandle) throws IOException;

    /**
     * Send initiate capture request to the PTP-Responder.
     *
     * @throws IOException
     */
    void initiateCapture() throws IOException;

    /**
     * Get the description of the device property of PTP-Responder.
     *
     * @param devicePropCode The code of the device property to get the description.
     * @return The description of device property.
     * @throws IOException
     */
    DevicePropDesc<?> getDevicePropDesc(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value of PTP-Responder.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException
     */
    InputStream getDevicePropValue(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT8.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException
     */
    UINT8 getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT16.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException
     */
    UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT32.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException
     */
    UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT64.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException
     */
    UINT64 getDevicePropValueAsUINT64(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as String.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException
     */
    String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Set device property value.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException;

    /**
     * Set device property value as UINT8.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, UINT8 value) throws IOException;

    /**
     * Set device property value as UINT16.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, UINT16 value) throws IOException;

    /**
     * Set device property value as UINT32.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, UINT32 value) throws IOException;

    /**
     * Set device property value as String.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, String value) throws IOException;

    /**
     * Terminate all capture.
     *
     * @throws IOException
     */
    void terminateOpenCapture() throws IOException;

    /**
     * Terminate specified capture.
     *
     * @param transactionID Transaction ID
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

    /**
     * Receive the response for previous operation from the PTP-Responder
     *
     * @return The response for previous operation from the PTP-Responder
     * @throws IOException
     */
    Response receiveResponse() throws IOException;

    /**
     * Receive response from PTP-Responder,
     * and throw PtpException if response code is not OK.
     *
     * @throws PtpException if response code is not OK
     * @throws IOException
     */
    void checkResponse() throws IOException;

    // Data

    /**
     * Send the PTP-Responder data.
     *
     * @param data Data to send
     * @throws IOException
     */
    void sendData(byte[] data) throws IOException;

    /**
     * Receive data from the PTP-Responder.
     *
     * @return Data which received from the PTP-Responder.
     * @throws IOException
     */
    InputStream receiveData() throws IOException;

    /**
     * Receive data from the PTP-Responder.
     *
     * @param dst The destination which to write data from the PTP-Responder.
     * @throws IOException
     */
    void receiveData(OutputStream dst) throws IOException;

    // Listener

    /**
     * Add the listener for PTP event.
     *
     * @param listener PtpEventListener to add to the list.
     * @return true if this initiator did not already contain the specified listener
     */
    boolean addListener(PtpEventListener listener);

    /**
     * Remove the listener for PTP event.
     *
     * @param listener PtpEventListener to remove from the list.
     * @return true if this initiator contained the specified listener
     */
    boolean removeListener(PtpEventListener listener);
}
