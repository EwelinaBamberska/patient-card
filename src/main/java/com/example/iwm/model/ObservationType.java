package com.example.iwm.model;

public enum ObservationType {
    WEIGHT("29463-7");

    private String code;

    ObservationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}