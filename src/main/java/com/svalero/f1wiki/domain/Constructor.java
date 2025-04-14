package com.svalero.f1wiki.domain;

import com.google.gson.annotations.SerializedName;

public class Constructor {

    @SerializedName("constructorId")
    private String constructorId;

    @SerializedName("name")
    private String name;

    @SerializedName("nationality")
    private String nationality;

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
