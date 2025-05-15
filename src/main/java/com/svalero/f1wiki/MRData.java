package com.svalero.f1wiki;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.table.RaceTable;

public class MRData {
    @SerializedName("RaceTable")
    private RaceTable raceTable;

    public RaceTable getRaceTable() {
        return raceTable;
    }

    public void setRaceTable(RaceTable raceTable) {
        this.raceTable = raceTable;
    }
}