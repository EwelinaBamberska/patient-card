package com.example.iwm.mapper;

import com.example.iwm.model.MedicationRequestDTO;
import org.hl7.fhir.r4.model.MedicationRequest;

public interface IMedicationRequestMapper {
    MedicationRequestDTO fromMedicationRequest(MedicationRequest medicationRequest);
}
