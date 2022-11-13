package com.parkway.model;

public class Element {
    private Property distance;
    private Property duration;
    private String status;

    public Property getDistance() {
        return distance;
    }

    public void setDistance(Property distance) {
        this.distance = distance;
    }

    public Property getDuration() {
        return duration;
    }

    public void setDuration(Property duration) {
        this.duration = duration;
    }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }
}
