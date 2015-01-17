package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

public class Event {
    private final UINT16 eventCode;
    private final UINT32 sessionID;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3;

    // Constructor

    public Event(UINT16 eventCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3) {
        Validators.validateNonNull("eventCode", eventCode);
        Validators.validateNonNull("sessionID", sessionID);
        Validators.validateNonNull("transactionID", transactionID);
        Validators.validateNonNull("p1", p1);
        Validators.validateNonNull("p2", p2);
        Validators.validateNonNull("p3", p3);

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!eventCode.equals(event.eventCode)) return false;
        if (!p1.equals(event.p1)) return false;
        if (!p2.equals(event.p2)) return false;
        if (!p3.equals(event.p3)) return false;
        if (!sessionID.equals(event.sessionID)) return false;
        if (!transactionID.equals(event.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventCode.hashCode();
        result = 31 * result + sessionID.hashCode();
        result = 31 * result + transactionID.hashCode();
        result = 31 * result + p1.hashCode();
        result = 31 * result + p2.hashCode();
        result = 31 * result + p3.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventCode=" + eventCode +
                ", sessionID=" + sessionID +
                ", transactionID=" + transactionID +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                '}';
    }
}
