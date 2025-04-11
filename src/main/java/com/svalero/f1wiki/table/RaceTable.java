package com.svalero.f1wiki.table;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.domain.Race;

import java.util.List;

public class RaceTable {
    @SerializedName("Races")
    private List<Race> races;

    public List<Race> getRaces() {
        return races;
    }
}