package com.example.iwm.model;

public class MedicationRequestDTO {
    private String id;
    private String version;
    private String status;
    private String reason;
    private String intent;
    private String category;
    private String priority;
    private String medication;
    private String patientId;
    private String creationDate;
    private String dosageInstruction;
    private String note;
    private String firstFillQuantity;
    private String firstFillDuration;
    private String dispenseInterval;
    private String validityPeriodStartDate;
    private String validityPeriodEndDate;
    private String numberOfRepeatsAllowed;
    private String quantity;
    private String expectedSupplyDuration;
    private String substitutionAllowed;
    private String substitutionReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDosageInstruction() {
        return dosageInstruction;
    }

    public void setDosageInstruction(String dosageInstruction) {
        this.dosageInstruction = dosageInstruction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFirstFillQuantity() {
        return firstFillQuantity;
    }

    public void setFirstFillQuantity(String firstFillQuantity) {
        this.firstFillQuantity = firstFillQuantity;
    }

    public String getFirstFillDuration() {
        return firstFillDuration;
    }

    public void setFirstFillDuration(String firstFillDuration) {
        this.firstFillDuration = firstFillDuration;
    }

    public String getDispenseInterval() {
        return dispenseInterval;
    }

    public void setDispenseInterval(String dispenseInterval) {
        this.dispenseInterval = dispenseInterval;
    }

    public String getValidityPeriodStartDate() {
        return validityPeriodStartDate;
    }

    public void setValidityPeriodStartDate(String validityPeriod) {
        this.validityPeriodStartDate = validityPeriod;
    }

    public String getValidityPeriodEndDate() {
        return validityPeriodEndDate;
    }

    public void setValidityPeriodEndDate(String validityPeriod) {
        this.validityPeriodEndDate = validityPeriod;
    }

    public String getNumberOfRepeatsAllowed() {
        return numberOfRepeatsAllowed;
    }

    public void setNumberOfRepeatsAllowed(String numberOfRepeatsAllowed) {
        this.numberOfRepeatsAllowed = numberOfRepeatsAllowed;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getExpectedSupplyDuration() {
        return expectedSupplyDuration;
    }

    public void setExpectedSupplyDuration(String expectedSupplyDuration) {
        this.expectedSupplyDuration = expectedSupplyDuration;
    }

    public String getSubstitutionAllowed() {
        return substitutionAllowed;
    }

    public void setSubstitutionAllowed(String substitutionAllowed) {
        this.substitutionAllowed = substitutionAllowed;
    }

    public String getSubstitutionReason() {
        return substitutionReason;
    }

    public void setSubstitutionReason(String substitutionReason) {
        this.substitutionReason = substitutionReason;
    }
}
