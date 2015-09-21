/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.data.Event;
import org.theta4j.util.Validators;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Set of PtpEventListener
 */
public final class PtpEventListenerSet extends AbstractSet<PtpEventListener> implements PtpEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpEventListenerSet.class);

    private final Set<PtpEventListener> listeners = new CopyOnWriteArraySet<>();

    // Set<PtpEventListener>

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(PtpEventListener listener) {
        return listeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        return listeners.remove(o);
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
    public Iterator<PtpEventListener> iterator() {
        return listeners.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return listeners.size();
    }

    // PtpEventListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(Event event) {
        Validators.notNull("event", event);

        for (Iterator<PtpEventListener> i = listeners.iterator(); i.hasNext(); ) {
            PtpEventListener listener = i.next();
            try {
                listener.onEvent(event);
            } catch (RuntimeException e) {
                LOGGER.error("Unexpected exception in listener", e);
                i.remove();
            }
        }
    }
}
