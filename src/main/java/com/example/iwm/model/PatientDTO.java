package com.example.iwm.model;

public class PatientDTO {
    private String id;
    private String version;
    private String name;
    private String birthDate;
    private String gender;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String lastName) {
        this.birthDate = lastName;
    }

    public void setGender(String PESEL) {
        this.gender = PESEL;
    }
}
