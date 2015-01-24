package com.theta360.theta;

import com.theta360.ptp.type.UINT32;

// TODO: Write Javadoc

/**
 * The listener interface for the events of RICOH THETA.
 *
 * @see com.theta360.theta.Theta#addListener(ThetaEventListener)
 * @see com.theta360.theta.Theta#removeListener(ThetaEventListener)
 */
public interface ThetaEventListener {
    /**
     * Notification of an object addition
     *
     * @param objectHandle The ObjectHandle of the added object.
     */
    void onObjectAdded(UINT32 objectHandle);

    /**
     * TODO: Replace this event with concrete events.
     * <p/>
     * https://developers.theta360.com/en/docs/ptpip_reference/
     * In the official document. The following properties will be notified when it is changed.
     * - CaptureStatus
     * - RecordingTime
     * - RemainingRecordingTime
     */
    void onDevicePropChanged(UINT32 devicePropCode);

    void onStoreFull(UINT32 storageID);

    void onCaptureComplete(UINT32 transactionID);
}
