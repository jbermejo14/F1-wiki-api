package com.svalero.f1wiki;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.table.DriverTable;

public class MRDataDriver {
    @SerializedName("DriverTable")
    private com.svalero.f1wiki.table.DriverTable DriverTable;

    public DriverTable getDriverTable() {
        return DriverTable;
    }
}
