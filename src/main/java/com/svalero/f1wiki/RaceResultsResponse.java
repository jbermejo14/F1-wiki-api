package com.svalero.f1wiki;

import com.google.gson.annotations.SerializedName;

public class RaceResultsResponse {
    @SerializedName("MRData")
    private MRData mRData;

    public MRData getMRData() {
        return mRData;
    }

    public void setMRData(MRData mRData) {
        this.mRData = mRData;
    }
}