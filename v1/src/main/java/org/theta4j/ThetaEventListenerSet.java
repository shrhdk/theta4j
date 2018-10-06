/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.code.EventCode;
import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

final class ThetaEventListenerSet extends AbstractSet<ThetaEventListener> implements ThetaEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaEventListener.class);

    private final Set<ThetaEventListener> listeners = new CopyOnWriteArraySet<>();

    // ThetaEventListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void onObjectAdded(UINT32 objectHandle) {
        for (Iterator<ThetaEventListener> i = listeners.iterator(); i.hasNext(); ) {
            ThetaEventListener listener = i.next();
            try {
                listener.onObjectAdded(objectHandle);
            } catch (RuntimeException e) {
                LOGGER.error("Unexpected exception in listener", e);
                i.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCaptureStatusChanged() {
        for (Iterator<ThetaEventListener> i = listeners.iterator(); i.hasNext(); ) {
            ThetaEventListener listener = i.next();
            try {
                listener.onCaptureStatusChanged();
            } catch (RuntimeException e) {
                LOGGER.error("Unexpected exception in listener", e);
                i.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRecordingTimeChanged() {
        for (Iterator<ThetaEventListener> i = listeners.iterator(); i.hasNext(); ) {
            ThetaEventListener listener = i.next();
            try {
                listener.onRecordingTimeChanged();
            } catch (RuntimeException e) {
                LOGGER.error("Unexpected exception in listener", e);
                i.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRemainingRecordingTimeChanged() {
        for (Iterator<ThetaEventListener> i = listeners.iterator(); i.hasNext(); ) {
            ThetaEventListener listener = i.next();
            try {
                listener.onRemainingRecordingTimeChanged();
            } catch (RuntimeException e) {
                LOGGER.error("Unexpected exception in listener", e);
                i.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStoreFull() {
        for (Iterator<ThetaEventListener> i = listeners.iterator(); i.hasNext(); ) {
            ThetaEventListener listener = i.next();
            try {
                listener.onStoreFull();
            } catch (RuntimeException e) {
                LOGGER.error("Unexpected exception in listener", e);
                i.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCaptureComplete(UINT32 transactionID) {
        for (Iterator<ThetaEventListener> i = listeners.iterator(); i.hasNext(); ) {
            ThetaEventListener listener = i.next();
            try {
                listener.onCaptureComplete(transactionID);
            } catch (RuntimeException e) {
                LOGGER.error("Unexpected exception in listener", e);
                i.remove();
            }
        }
    }

    // Set

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(ThetaEventListener listener) {
        return listeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object object) {
        return listeners.remove(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<ThetaEventListener> iterator() {
        return listeners.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        listeners.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return listeners.size();
    }

    // Utility method

    public void raise(Event event) {
        Validators.notNull("event", event);

        UINT16 eventCode = event.getEventCode();
        UINT32 p1 = event.getP1();

        if (eventCode.equals(EventCode.OBJECT_ADDED.value())) {
            onObjectAdded(p1);
        } else if (eventCode.equals(EventCode.DEVICE_PROP_CHANGED.value())) {
            onDevicePropChanged(new UINT16(p1.intValue()));
        } else if (eventCode.equals(EventCode.STORE_FULL.value())) {
            onStoreFull();
        } else if (eventCode.equals(EventCode.CAPTURE_COMPLETE.value())) {
            onCaptureComplete(p1);
        } else {
            LOGGER.warn("Unknown EventCode: {}", eventCode);
        }
    }

    private void onDevicePropChanged(UINT16 devicePropCode) {
        if (DevicePropCode.CAPTURE_STATUS.value().equals(devicePropCode)) {
            onCaptureStatusChanged();
        } else if (DevicePropCode.RECORDING_TIME.value().equals(devicePropCode)) {
            onRecordingTimeChanged();
        } else if (DevicePropCode.REMAINING_RECORDING_TIME.value().equals(devicePropCode)) {
            onRemainingRecordingTimeChanged();
        } else {
            LOGGER.warn("Unknown DevicePropCode: {}", devicePropCode);
        }
    }
}
