/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

/**
 * The event class defined in PTP standard.
 */
public class Event {
    private final UINT16 eventCode;
    private final UINT32 sessionID;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3;

    // Constructor

    /**
     * Constructs new event object.
     *
     * @param eventCode     The event code of this event.
     * @param sessionID     The session ID of this event.
     * @param transactionID The transaction ID of this event.
     * @throws NullPointerException if an argument is null.
     */
    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID) {
        this(eventCode, sessionID, transactionID, UINT32.ZERO);
    }

    /**
     * Constructs new event object.
     *
     * @param eventCode     The event code of this event.
     * @param sessionID     The session ID of this event.
     * @param transactionID The transaction ID of this event.
     * @param p1            The 1st parameter of this event.
     * @throws NullPointerException if an argument is null.
     */
    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1) {
        this(eventCode, sessionID, transactionID, p1, UINT32.ZERO);
    }

    /**
     * Constructs new event object.
     *
     * @param eventCode     The event code of this event.
     * @param sessionID     The session ID of this event.
     * @param transactionID The transaction ID of this event.
     * @param p1            The 1st parameter of this event.
     * @param p2            The 2nd parameter of this event.
     * @throws NullPointerException if an argument is null.
     */
    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2) {
        this(eventCode, sessionID, transactionID, p1, p2, UINT32.ZERO);
    }

    /**
     * Constructs new event object.
     *
     * @param eventCode     The event code of this event.
     * @param sessionID     The session ID of this event.
     * @param transactionID The transaction ID of this event.
     * @param p1            The 1st parameter of this event.
     * @param p2            The 2nd parameter of this event.
     * @param p3            The 3rd parameter of this event.
     * @throws NullPointerException if an argument is null.
     */
    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3) {
        Validators.notNull("eventCode", eventCode);
        Validators.notNull("sessionID", sessionID);
        Validators.notNull("transactionID", transactionID);
        Validators.notNull("p1", p1);
        Validators.notNull("p2", p2);
        Validators.notNull("p3", p3);

        this.eventCode = eventCode;
        this.sessionID = sessionID;
        this.transactionID = transactionID;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    // Getter

    /**
     * Returns the event code of this event.
     */
    public UINT16 getEventCode() {
        return eventCode;
    }

    /**
     * Returns the session ID of this event.
     */
    public UINT32 getSessionID() {
        return sessionID;
    }

    /**
     * Returns the transaction ID of this event.
     */
    public UINT32 getTransactionID() {
        return transactionID;
    }

    /**
     * Returns the 1st parameter of this event.
     */
    public UINT32 getP1() {
        return p1;
    }

    /**
     * Returns the 2nd parameter of this event.
     */
    public UINT32 getP2() {
        return p2;
    }

    /**
     * Returns the 3rd parameter of this event.
     */
    public UINT32 getP3() {
        return p3;
    }

    // Basic Method

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event rhs = (Event) o;

        return new EqualsBuilder()
                .append(eventCode, rhs.eventCode)
                .append(sessionID, rhs.sessionID)
                .append(transactionID, rhs.transactionID)
                .append(p1, rhs.p1)
                .append(p2, rhs.p2)
                .append(p3, rhs.p3)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(eventCode)
                .append(sessionID)
                .append(transactionID)
                .append(p1)
                .append(p2)
                .append(p3)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
