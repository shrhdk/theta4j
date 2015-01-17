package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;

public class Response {
    private final UINT16 responseCode;
    private final UINT32 sessionID;
    private final UINT32 transactionID;
    private final UINT32 p1, p2, p3, p4, p5;

    // Constructor

    public Response(UINT16 responseCode, UINT32 sessionID, UINT32 transactionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) {
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

    public UINT16 getResponseCode() {
        return responseCode;
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

    public UINT32 getP4() {
        return p4;
    }

    public UINT32 getP5() {
        return p5;
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (!p1.equals(response.p1)) return false;
        if (!p2.equals(response.p2)) return false;
        if (!p3.equals(response.p3)) return false;
        if (!p4.equals(response.p4)) return false;
        if (!p5.equals(response.p5)) return false;
        if (!responseCode.equals(response.responseCode)) return false;
        if (!sessionID.equals(response.sessionID)) return false;
        if (!transactionID.equals(response.transactionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = responseCode.hashCode();
        result = 31 * result + sessionID.hashCode();
        result = 31 * result + transactionID.hashCode();
        result = 31 * result + p1.hashCode();
        result = 31 * result + p2.hashCode();
        result = 31 * result + p3.hashCode();
        result = 31 * result + p4.hashCode();
        result = 31 * result + p5.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseCode=" + responseCode +
                ", sessionID=" + sessionID +
                ", transactionID=" + transactionID +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                '}';
    }
}
