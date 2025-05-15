package com.svalero.f1wiki.domain;

import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("driverId")
    private String driverId;

    @SerializedName("givenName")
    private String givenName;

    @SerializedName("familyName")
    private String familyName;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("permanentNumber")
    private String permanentNumber;

    @SerializedName("code")
    private String code;

    public String getFullName() {
        return givenName + " " + familyName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPermanentNumber() {
        return permanentNumber;
    }

    public void setPermanentNumber(String permanentNumber) {
        this.permanentNumber = permanentNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
