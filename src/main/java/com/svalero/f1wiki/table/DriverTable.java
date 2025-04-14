package com.svalero.f1wiki.table;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.domain.Driver;

import java.util.List;

public class DriverTable {
    @SerializedName("Drivers")
    private List<Driver> Drivers;

    public List<Driver> getDrivers() {
        return Drivers;
    }
}
