package com.svalero.f1wiki;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.table.ConstructorTable;

public class MRDataConstructor {
    @SerializedName("ConstructorTable")
    private ConstructorTable ConstructorTable;

    public ConstructorTable getConstructorTable() {
        return ConstructorTable;
    }
}

