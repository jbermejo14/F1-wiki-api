package com.svalero.f1wiki;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaceTable {
    @SerializedName("Races")
    private List<Race> races;

    public List<Race> getRaces() {
        return races;
    }
}