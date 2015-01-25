package com.theta360.theta;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

final class ThetaEventListenerSet extends AbstractSet<ThetaEventListener> implements ThetaEventListener {
    private final Set<ThetaEventListener> listeners = new CopyOnWriteArraySet<>();

    // ThetaEventListener

    @Override
    public void onObjectAdded(long objectHandle) {
        for (ThetaEventListener listener : listeners) {
            listener.onObjectAdded(objectHandle);
        }
    }

    @Override
    public void onCaptureStatusChanged() {
        for (ThetaEventListener listener : listeners) {
            listener.onCaptureStatusChanged();
        }
    }

    @Override
    public void onRecordingTimeChanged() {
        for (ThetaEventListener listener : listeners) {
            listener.onRecordingTimeChanged();
        }
    }

    @Override
    public void onRemainingRecordingTimeChanged() {
        for (ThetaEventListener listener : listeners) {
            listener.onRemainingRecordingTimeChanged();
        }
    }

    @Override
    public void onStoreFull(long storageID) {
        for (ThetaEventListener listener : listeners) {
            listener.onStoreFull(storageID);
        }
    }

    @Override
    public void onCaptureComplete(long transactionID) {
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
