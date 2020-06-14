package com.example.iwm.model;

import java.util.ArrayList;
import java.util.List;

public class DosageInstructionDTO {
    private String sequence;
    private String frequency;
    private String period;
    private String periodUnit;
    private List<DoseAndRateDTO> doseAndRate = new ArrayList<>();

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }

    public List<DoseAndRateDTO> getDoseAndRate() {
        return doseAndRate;
    }

    public void setDoseAndRate(List<DoseAndRateDTO> doseAndRate) {
        this.doseAndRate = doseAndRate;
    }
}
