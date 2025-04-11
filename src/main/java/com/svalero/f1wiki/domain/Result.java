package com.svalero.f1wiki.domain;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("Driver")
    private Driver driver;

    private String position;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}