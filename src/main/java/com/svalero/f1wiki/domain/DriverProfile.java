package com.svalero.f1wiki.domain;

public class DriverProfile {
    private Driver ergastDriver;
    private String headshotUrl;

    public DriverProfile(Driver ergastDriver, String headshotUrl) {
        this.ergastDriver = ergastDriver;
        this.headshotUrl = headshotUrl;
    }

    public Driver getErgastDriver() {
        return ergastDriver;
    }

    public String getHeadshotUrl() {
        return headshotUrl;
    }
}
