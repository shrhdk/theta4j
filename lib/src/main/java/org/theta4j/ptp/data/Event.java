package org.theta4j.ptp.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

public class Event {
    private final UINT16 eventCode;
    private final UINT32 sessionID;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3;

    // Constructor

    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID) {
        this(eventCode, sessionID, transactionID, UINT32.ZERO);
    }

    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1) {
        this(eventCode, sessionID, transactionID, p1, UINT32.ZERO);
    }

    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2) {
        this(eventCode, sessionID, transactionID, p1, p2, UINT32.ZERO);
    }

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

    public UINT16 getEventCode() {
        return eventCode;
    }

    public UINT32 getSessionID() {
        return sessionID;
    }

    public UINT32 getTransactionID() {
        return transactionID;
    }

    public UINT32 getP1() {
        return p1;
    }

    public UINT32 getP2() {
        return p2;
    }

    public UINT32 getP3() {
        return p3;
    }

    // Basic Method

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
