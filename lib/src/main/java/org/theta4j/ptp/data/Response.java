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
 * The response class defined in PTP standard.
 */
public class Response {
    private final UINT16 responseCode;
    private final UINT32 sessionID;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3, p4, p5;

    // Constructor

    /**
     * Constructs new PTP response object.
     *
     * @param responseCode  The response code of this response.
     * @param sessionID     The session ID of this response.
     * @param transactionID The transaction ID of this response.
     * @throws NullPointerException if an argument is null.
     */
    public Response(UINT16 responseCode, UINT32 sessionID, UINT32 transactionID) {
        this(responseCode, sessionID, transactionID, UINT32.ZERO);
    }

    /**
     * Constructs new PTP response object.
     *
     * @param responseCode  The response code of this response.
     * @param sessionID     The session ID of this response.
     * @param transactionID The transaction ID of this response.
     * @param p1            The 1st parameter of this response.
     * @throws NullPointerException if an argument is null.
     */
    public Response(UINT16 responseCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1) {
        this(responseCode, sessionID, transactionID, p1, UINT32.ZERO);
    }

    /**
     * Constructs new PTP response object.
     *
     * @param responseCode  The response code of this response.
     * @param sessionID     The session ID of this response.
     * @param transactionID The transaction ID of this response.
     * @param p1            The 1st parameter of this response.
     * @param p2            The 2nd parameter of this response.
     * @throws NullPointerException if an argument is null.
     */
    public Response(UINT16 responseCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2) {
        this(responseCode, sessionID, transactionID, p1, p2, UINT32.ZERO);
    }

    /**
     * Constructs new PTP response object.
     *
     * @param responseCode  The response code of this response.
     * @param sessionID     The session ID of this response.
     * @param transactionID The transaction ID of this response.
     * @param p1            The 1st parameter of this response.
     * @param p2            The 2nd parameter of this response.
     * @param p3            The 3rd parameter of this response.
     * @throws NullPointerException if an argument is null.
     */
    public Response(UINT16 responseCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3) {
        this(responseCode, sessionID, transactionID, p1, p2, p3, UINT32.ZERO);
    }

    /**
     * Constructs new PTP response object.
     *
     * @param responseCode  The response code of this response.
     * @param sessionID     The session ID of this response.
     * @param transactionID The transaction ID of this response.
     * @param p1            The 1st parameter of this response.
     * @param p2            The 2nd parameter of this response.
     * @param p3            The 3rd parameter of this response.
     * @param p4            The 4th parameter of this response.
     * @throws NullPointerException if an argument is null.
     */
    public Response(UINT16 responseCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) {
        this(responseCode, sessionID, transactionID, p1, p2, p3, p4, UINT32.ZERO);
    }

    /**
     * Constructs new PTP response object.
     *
     * @param responseCode  The response code of this response.
     * @param sessionID     The session ID of this response.
     * @param transactionID The transaction ID of this response.
     * @param p1            The 1st parameter of this response.
     * @param p2            The 2nd parameter of this response.
     * @param p3            The 3rd parameter of this response.
     * @param p4            The 4th parameter of this response.
     * @param p5            The 5th parameter of this response.
     * @throws NullPointerException if an argument is null.
     */
    public Response(UINT16 responseCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) {
        Validators.notNull("responseCode", responseCode);
        Validators.notNull("sessionID", sessionID);
        Validators.notNull("transactionID", transactionID);
        Validators.notNull("p1", p1);
        Validators.notNull("p2", p2);
        Validators.notNull("p3", p3);
        Validators.notNull("p4", p4);
        Validators.notNull("p5", p5);

        this.responseCode = responseCode;
        this.sessionID = sessionID;
        this.transactionID = transactionID;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
    }

    // Getter

    /**
     * Returns the response code of this response.
     */
    public UINT16 getResponseCode() {
        return responseCode;
    }

    /**
     * Returns the session ID of this response.
     */
    public UINT32 getSessionID() {
        return sessionID;
    }

    /**
     * Returns the transaction ID of this response.
     */
    public UINT32 getTransactionID() {
        return transactionID;
    }

    /**
     * Returns the 1st parameter of this response.
     */
    public UINT32 getP1() {
        return p1;
    }

    /**
     * Returns the 2nd parameter of this response.
     */
    public UINT32 getP2() {
        return p2;
    }

    /**
     * Returns the 3rd parameter of this response.
     */
    public UINT32 getP3() {
        return p3;
    }

    /**
     * Returns the 4th parameter of this response.
     */
    public UINT32 getP4() {
        return p4;
    }

    /**
     * Returns the 5th parameter of this response.
     */
    public UINT32 getP5() {
        return p5;
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

        Response rhs = (Response) o;

        return new EqualsBuilder()
                .append(responseCode, rhs.responseCode)
                .append(sessionID, rhs.sessionID)
                .append(transactionID, rhs.transactionID)
                .append(p1, rhs.p1)
                .append(p2, rhs.p2)
                .append(p3, rhs.p3)
                .append(p4, rhs.p4)
                .append(p5, rhs.p5)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(responseCode)
                .append(sessionID)
                .append(transactionID)
                .append(p1)
                .append(p2)
                .append(p3)
                .append(p4)
                .append(p5)
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
