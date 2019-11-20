package com.thirty.model;

public class Points {

    private int mPointId;
    private boolean pointTaken;

    public Points(int pointId) {
        mPointId = pointId;
        pointTaken = false;
    }

    public int getPointId() {
        return mPointId;
    }

    public void setPointId(int pointId) {
        mPointId = pointId;
    }

    public boolean isPointTaken() {
        return pointTaken;
    }

    public void setPointTaken(boolean pointTaken) {
        this.pointTaken = pointTaken;
    }
}
