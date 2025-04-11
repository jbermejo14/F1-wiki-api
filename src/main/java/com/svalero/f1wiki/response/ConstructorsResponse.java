package com.svalero.f1wiki.response;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.MRDataConstructor;

public class ConstructorsResponse {
    @SerializedName("MRData")
    private MRDataConstructor MRData;

    public MRDataConstructor getMRData() {
        return MRData;
    }
}