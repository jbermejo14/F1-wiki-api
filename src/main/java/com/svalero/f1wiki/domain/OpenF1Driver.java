package com.svalero.f1wiki.domain;

import com.google.gson.annotations.SerializedName;

public class OpenF1Driver {
    @SerializedName("driver_number")
    private int driverNumber;

    @SerializedName("headshot_url")
    private String headshotUrl;

    public int getDriverNumber() {
        return driverNumber;
    }

    public String getHeadshotUrl() {
        return headshotUrl;
    }
}
