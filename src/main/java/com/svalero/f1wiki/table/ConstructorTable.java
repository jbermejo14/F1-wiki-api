package com.svalero.f1wiki.table;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorTable {
    @SerializedName("Constructors")
    private List<Constructor> Constructors;

    public List<Constructor> getConstructors() {
        return Constructors;
    }
}
