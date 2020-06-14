package com.example.iwm.model;

import java.util.ArrayList;
import java.util.List;

public class DoseAndRateDTO {
    private List<String> doseRateType = new ArrayList<>();
    private String doseQuantity;

    public List<String> getDoseRateType() {
        return doseRateType;
    }

    public void setDoseRateType(List<String> doseRateType) {
        this.doseRateType = doseRateType;
    }

    public String getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(String doseQuantity) {
        this.doseQuantity = doseQuantity;
    }
}
