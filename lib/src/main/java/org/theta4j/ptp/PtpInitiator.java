/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.data.DeviceInfo;
import org.theta4j.ptp.data.Response;
import org.theta4j.ptp.type.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface of PTP initiator defined in PTP standard.
 */
public interface PtpInitiator extends Closeable {
    // Operation (Base)

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @return Transaction ID
     * @throws IOException          if an I/O error occurs while sending the operation.
     * @throws NullPointerException if operationCode is null.
     */
    UINT32 sendOperation(Code<UINT16> operationCode) throws IOException;

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @param p1            Parameter 1
     * @return Transaction ID
     * @throws IOException          if an I/O error occurs while sending the operation.
     * @throws NullPointerException if an argument is null.
     */
    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1) throws IOException;

    /**
     * Send PTP-Responder operation request.
     *
     * @param operationCode Operation code
     * @param p1            Parameter 1
     * @param p2            Parameter 2
     * @return Transaction ID
     * @throws IOException          if an I/O error occurs while sending the operation.
     * @throws NullPointerException if an argument is null.
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
     * @throws IOException          if an I/O error occurs while sending the operation.
     * @throws NullPointerException if an argument is null.
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
     * @throws IOException          if an I/O error occurs while sending the operation.
     * @throws NullPointerException if an argument is null.
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
     * @throws IOException          if an I/O error occurs while sending the operation.
     * @throws NullPointerException if an argument is null.
     */
    UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) throws IOException;

    // Operation

    /**
     * Get the DeviceInfo of the PTP-Responder.
     *
     * @return Device information of the PTP-Responder
     * @throws IOException  if an I/O error occurs while getting the device information.
     * @throws PtpException if the PTP response is not OK.
     */
    DeviceInfo getDeviceInfo() throws IOException;

    /**
     * Open the session.
     *
     * @param sessionID Session ID
     * @throws IOException          if an I/O error occurs while opening the session.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if sessionID is null.
     */
    void openSession(UINT32 sessionID) throws IOException;

    /**
     * Close the session.
     *
     * @throws IOException  if an I/O error occurs while closing the session.
     * @throws PtpException if the PTP response is not OK.
     */
    void closeSession() throws IOException;


    // Device Property

    /**
     * Get device property value of PTP-Responder.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    InputStream getDevicePropValue(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as INT8.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    INT8 getDevicePropValueAsINT8(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT8.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    UINT8 getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as INT16.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    INT16 getDevicePropValueAsINT16(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT16.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as INT32.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    INT32 getDevicePropValueAsINT32(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT32.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as INT64.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    INT64 getDevicePropValueAsINT64(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT64.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    UINT64 getDevicePropValueAsUINT64(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as INT128.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    INT128 getDevicePropValueAsINT128(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as UINT128.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    UINT128 getDevicePropValueAsUINT128(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Get device property value as String.
     *
     * @param devicePropCode The code of the device property to get value.
     * @return Device property value
     * @throws IOException          if an I/O error occurs while getting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if devicePropCode is null.
     */
    String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException;

    /**
     * Set device property value.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException          if an I/O error occurs while setting the device property.
     * @throws NullPointerException if an argument is null.
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException;

    /**
     * Set device property value.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException          if an I/O error occurs while setting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if an argument is null.
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, PtpInteger value) throws IOException;

    /**
     * Set device property value as String.
     *
     * @param devicePropCode The code of the device property to set value.
     * @throws IOException          if an I/O error occurs while setting the device property.
     * @throws PtpException         if the PTP response is not OK.
     * @throws NullPointerException if an argument is null.
     */
    void setDevicePropValue(Code<UINT16> devicePropCode, String value) throws IOException;

    // Responses

    /**
     * Receive the response for previous operation from the PTP-Responder
     *
     * @return The response for previous operation from the PTP-Responder
     * @throws IOException           if an I/O error occurs while receiving the response.
     * @throws IllegalStateException if the transaction sequence is not on the Response Phase.
     */
    Response receiveResponse() throws IOException;

    /**
     * Receive response from PTP-Responder,
     * and throw PtpException if response code is not OK.
     *
     * @throws IOException  if an I/O error occurs while receiving the response.
     * @throws PtpException if response code is not OK
     */
    Response checkAndReadResponse() throws IOException;

    // Data

    /**
     * Send the PTP-Responder data.
     *
     * @param data Data to send
     * @throws IOException          if an I/O error occurs while sending data.
     * @throws NullPointerException if data is null.
     */
    void sendData(byte[] data) throws IOException;

    /**
     * Receive data from the PTP-Responder.
     *
     * @return Data which received from the PTP-Responder.
     * @throws IOException if an I/O error occurs while receiving data.
     */
    InputStream receiveData() throws IOException;

    /**
     * Receive data from the PTP-Responder.
     *
     * @param dst The destination which to write data from the PTP-Responder.
     * @throws IOException          if an I/O error occurs while receiving data.
     * @throws NullPointerException if dst is null.
     */
    void receiveData(OutputStream dst) throws IOException;

    // Listener

    /**
     * Add the listener for PTP event.
     *
     * @param listener PtpEventListener to add to the list.
     * @return true if this initiator did not already contain the specified listener
     * @throws NullPointerException if listener is null.
     */
    boolean addListener(PtpEventListener listener);

    /**
     * Remove the listener for PTP event.
     *
     * @param listener PtpEventListener to remove from the list.
     * @return true if this initiator contained the specified listener
     * @throws NullPointerException if listener is null.
     */
    boolean removeListener(PtpEventListener listener);
}
