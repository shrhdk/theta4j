package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;

public class Response {
    private final UINT16 code;
    private final UINT32 sessionID;
    private final UINT32 p1, p2, p3, p4, p5;

    // Constructor

    public Response(UINT16 code, UINT32 sessionID, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) {
        this.code = code;
        this.sessionID = sessionID;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (!code.equals(response.code)) return false;
        if (!p1.equals(response.p1)) return false;
        if (!p2.equals(response.p2)) return false;
        if (!p3.equals(response.p3)) return false;
        if (!p4.equals(response.p4)) return false;
        if (!p5.equals(response.p5)) return false;
        if (!sessionID.equals(response.sessionID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + sessionID.hashCode();
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
                "code=" + code +
                ", sessionID=" + sessionID +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                '}';
    }
}
