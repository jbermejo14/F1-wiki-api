package com.svalero.f1wiki.table;

import com.google.gson.annotations.SerializedName;
import com.svalero.f1wiki.domain.Constructor;


import java.util.List;

public class ConstructorTable {
    @SerializedName("Constructors")
    private List<Constructor> Constructors;

    public List<Constructor> getConstructors() {
        return Constructors;
    }
}
