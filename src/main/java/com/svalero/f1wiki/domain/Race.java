package com.svalero.f1wiki.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Race {
    @SerializedName("raceName")
    private String raceName;

    @SerializedName("Results")
    private List<Result> results;

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
