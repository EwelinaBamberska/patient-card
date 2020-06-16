package com.example.iwm.model;

import java.util.ArrayList;
import java.util.List;

public class MedicationRequestDTO {
    private String id;
    private String version;
    private String status;
    private String codeableConcept;
    private String intent;
    private String requester;
    private String patientId;
    private String creationDate;
    private List<DosageInstructionDTO> dosageInstruction = new ArrayList<>();

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

    public String getCodeableConcept() {
        return codeableConcept;
    }

    public void setCodeableConcept(String codeableConcept) {
        this.codeableConcept = codeableConcept;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
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

    public List<DosageInstructionDTO> getDosageInstruction() {
        return dosageInstruction;
    }

    public void setDosageInstruction(List<DosageInstructionDTO> dosageInstruction) {
        this.dosageInstruction = dosageInstruction;
    }
}
