package com.theta360.theta;

import com.theta360.ptp.type.UINT32;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

final class ThetaEventListenerSet extends AbstractSet<ThetaEventListener> implements ThetaEventListener {
    private final Set<ThetaEventListener> listeners = new CopyOnWriteArraySet<>();

    // ThetaEventListener

    @Override
    public void onObjectAdded(UINT32 objectHandle) {
        for (ThetaEventListener listener : listeners) {
            listener.onObjectAdded(objectHandle);
        }
    }

    @Override
    public void onDevicePropChanged(UINT32 devicePropCode) {
        for (ThetaEventListener listener : listeners) {
            listener.onDevicePropChanged(devicePropCode);
        }
    }

    @Override
    public void onStoreFull(UINT32 storageID) {
        for (ThetaEventListener listener : listeners) {
            listener.onStoreFull(storageID);
        }
    }

    @Override
    public void onCaptureComplete(UINT32 transactionID) {
        for (ThetaEventListener listener : listeners) {
            listener.onCaptureComplete(transactionID);
        }
    }

    // Set

    @Override
    public int size() {
        return listeners.size();
    }

    @Override
    public boolean add(ThetaEventListener listener) {
        return listeners.add(listener);
    }

    @Override
    public Iterator<ThetaEventListener> iterator() {
        return listeners.iterator();
    }

    @Override
    public void clear() {
        listeners.clear();
    }
}
